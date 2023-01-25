package com.snmi.service;

import com.snmi.component.LikesComponent;
import com.snmi.component.PostComponent;
import com.snmi.dto.KafkaHistoryDTO;
import com.snmi.dto.LikeDTO;
import com.snmi.enums.Count;
import com.snmi.enums.HistoryType;
import com.snmi.mapper.LikesMapper;
import com.snmi.model.Likes;
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
import static com.snmi.constants.GlobalConstants.EMPTY_STRING;
import static com.snmi.constants.GlobalConstants.LOG_REQUEST;
import static com.snmi.constants.GlobalConstants.LOG_RESPONSE;
import static com.snmi.constants.KafkaGlobalConstants.HISTORY_TOPIC;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikesService {

    private static final Logger LOG = LoggerFactory.getLogger(LikesService.class);

    private static final String LIKE = "like";

    private final LikesMapper likesMapper;
    private final KafkaService kafkaService;
    private final PostComponent postComponent;
    private final LikesComponent likesComponent;

    @Transactional
    public LikeDTO createLike(final LikeDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(CREATE_FUNCTIONALITY, LIKE), dto));

        postComponent.throwExceptionIfThePostIsHidden(dto.getPostId());
        likesComponent.throwExceptionIfTheInteractionAlreadyExists(dto.getUsername(), dto.getPostId());
        Post post = postComponent.getByIdOrException(dto.getPostId());
        post.setLikeCount(post.getLikeCount() + DEFAULT_COUNTER);
        Likes likes = likesMapper.toModel(dto);
        likes.setPost(postComponent.save(post));
        final LikeDTO response = likesMapper.toDTO(likesComponent.save(likes));
        sendKafkaMessage(likes.getUsername(), post.getId(), post.getTitle(), Count.INCREASE);

        LOG.info(String.format(LOG_RESPONSE, String.format(CREATE_FUNCTIONALITY, LIKE), response));

        return response;
    }

    @Transactional
    public void deleteLike(final String id) {
        LOG.info(String.format(LOG_REQUEST, String.format(DELETE_FUNCTIONALITY, LIKE), id));

        final Likes likes = likesComponent.getByIdOrException(id);
        AccessValidator.controlYourData(likes.getUsername());
        postComponent.throwExceptionIfThePostIsHidden(likes.getPostId());
        Post post = postComponent.getByIdOrException(likes.getPostId());
        post.setLikeCount(post.getLikeCount() - DEFAULT_COUNTER);
        postComponent.save(post);
        likesComponent.deleteById(id);
        sendKafkaMessage(likes.getUsername(), post.getId(), post.getTitle(), Count.DECREASE);

        LOG.info(String.format(LOG_RESPONSE, String.format(DELETE_FUNCTIONALITY, LIKE), EMPTY_STRING));
    }

    private void sendKafkaMessage(final String username, final String postId, final String postTitle, final Count action) {
        kafkaService.send(HISTORY_TOPIC, new KafkaHistoryDTO(username, postId, postTitle, HistoryType.LIKE, action));
    }

}
