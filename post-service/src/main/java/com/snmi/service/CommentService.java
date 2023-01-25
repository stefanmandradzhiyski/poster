package com.snmi.service;

import com.snmi.component.CommentComponent;
import com.snmi.component.PostComponent;
import com.snmi.dto.CommentDTO;
import com.snmi.dto.KafkaHistoryDTO;
import com.snmi.enums.Count;
import com.snmi.enums.HistoryType;
import com.snmi.mapper.CommentMapper;
import com.snmi.model.Comment;
import com.snmi.model.Post;
import com.snmi.util.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.snmi.constants.GlobalConstants.CREATE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.DEFAULT_COUNTER;
import static com.snmi.constants.GlobalConstants.DELETE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.LOG_REQUEST;
import static com.snmi.constants.GlobalConstants.LOG_RESPONSE;
import static com.snmi.constants.GlobalConstants.UPDATE_FUNCTIONALITY;
import static com.snmi.constants.KafkaGlobalConstants.HISTORY_TOPIC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private static final Logger LOG = LoggerFactory.getLogger(CommentService.class);

    private static final String COMMENT = "comment";

    private final KafkaService kafkaService;
    private final CommentMapper commentMapper;
    private final PostComponent postComponent;
    private final CommentComponent commentComponent;

    @Transactional
    public CommentDTO createComment(final CommentDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(CREATE_FUNCTIONALITY, COMMENT), dto));

        postComponent.throwExceptionIfThePostIsHidden(dto.getPostId());
        commentComponent.throwExceptionIfTheInteractionAlreadyExists(dto.getUsername(), dto.getPostId());
        Post post = postComponent.getByIdOrException(dto.getPostId());
        post.setCommentCount(post.getCommentCount() + DEFAULT_COUNTER);
        post.setTotalRatingPoints(post.getTotalRatingPoints() + dto.getRating());
        post.setOverallRating((double) post.getTotalRatingPoints() / (double) post.getCommentCount());
        Comment comment = commentMapper.toModel(dto);
        comment.setPost(postComponent.save(post));
        final CommentDTO response = commentMapper.toDTO(commentComponent.save(comment));
        sendKafkaMessage(comment.getUsername(), post.getId(), post.getTitle(), Count.INCREASE);

        LOG.info(String.format(LOG_RESPONSE, String.format(CREATE_FUNCTIONALITY, COMMENT), response));

        return response;
    }

    @Transactional
    public CommentDTO updateComment(final CommentDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(UPDATE_FUNCTIONALITY, COMMENT), dto));

        Comment comment = commentComponent.getByIdOrException(dto.getId());
        AccessValidator.controlYourData(comment.getUsername());
        postComponent.throwExceptionIfThePostIsHidden(comment.getPostId());
        Post post = postComponent.getByIdOrException(comment.getPostId());
        post.setTotalRatingPoints(post.getTotalRatingPoints() - comment.getRating() + dto.getRating());
        post.setOverallRating((double) post.getTotalRatingPoints() / (double) post.getCommentCount());
        postComponent.save(post);
        comment.setContent(dto.getContent());
        comment.setRating(dto.getRating());
        final CommentDTO response = commentMapper.toDTO(commentComponent.save(comment));

        LOG.info(String.format(LOG_RESPONSE, String.format(UPDATE_FUNCTIONALITY, COMMENT), response));

        return response;
    }

    @Transactional
    public void deleteComment(final String id) {
        LOG.info(String.format(LOG_REQUEST, String.format(DELETE_FUNCTIONALITY, COMMENT), id));

        final Comment comment = commentComponent.getByIdOrException(id);
        AccessValidator.controlYourData(comment.getUsername());
        postComponent.throwExceptionIfThePostIsHidden(comment.getPostId());
        Post post = postComponent.getByIdOrException(comment.getPostId());
        post.setCommentCount(post.getCommentCount() - DEFAULT_COUNTER);
        post.setTotalRatingPoints(post.getTotalRatingPoints() - comment.getRating());
        post.setOverallRating((double) post.getTotalRatingPoints() / (double) post.getCommentCount());
        postComponent.save(post);
        commentComponent.deleteById(id);
        sendKafkaMessage(comment.getUsername(), post.getId(), post.getTitle(), Count.DECREASE);

        LOG.info(String.format(LOG_RESPONSE, String.format(DELETE_FUNCTIONALITY, COMMENT), id));
    }

    private void sendKafkaMessage(final String username, final String postId, final String postTitle, final Count action) {
        kafkaService.send(HISTORY_TOPIC, new KafkaHistoryDTO(username, postId, postTitle, HistoryType.COMMENT, action));
    }

}
