package com.snmi.service;

import com.snmi.component.CommentComponent;
import com.snmi.component.LikesComponent;
import com.snmi.component.PostComponent;
import com.snmi.dto.BatchPostDisplayDTO;
import com.snmi.dto.CommentDTO;
import com.snmi.dto.KafkaUserCountDTO;
import com.snmi.dto.LikeDTO;
import com.snmi.dto.PostDTO;
import com.snmi.dto.PostDisplayDTO;
import com.snmi.dto.PostSearchRequestDTO;
import com.snmi.enums.Count;
import com.snmi.mapper.CommentMapper;
import com.snmi.mapper.LikesMapper;
import com.snmi.mapper.PostMapper;
import com.snmi.model.Post;
import com.snmi.util.AccessValidator;
import com.snmi.wrapper.PostSearchRequestWrapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static com.snmi.constants.GlobalConstants.CREATE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.DELETE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.GET_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.LOG_REQUEST;
import static com.snmi.constants.GlobalConstants.LOG_RESPONSE;
import static com.snmi.constants.GlobalConstants.PATCH_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.SEARCH_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.UPDATE_FUNCTIONALITY;
import static com.snmi.constants.KafkaGlobalConstants.USER_TOPIC;
import static java.util.stream.Collectors.groupingBy;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {

    private static final Logger LOG = LoggerFactory.getLogger(PostService.class);

    private static final String POST = "post";
    private static final String LIKE_FUNCTIONALITY = "Show likes functionality";
    private static final String COMMENT_FUNCTIONALITY = "Show comments functionality";

    private final PostMapper postMapper;
    private final LikesMapper likesMapper;
    private final KafkaService kafkaService;
    private final CommentMapper commentMapper;
    private final PostComponent postComponent;
    private final LikesComponent likesComponent;
    private final CommentComponent commentComponent;

    @Transactional
    public PostDTO createPost(final PostDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(CREATE_FUNCTIONALITY, POST), dto));

        final PostDTO response = postMapper.toDTO(postComponent.save(postMapper.toModel(dto)));
        kafkaService.send(USER_TOPIC, new KafkaUserCountDTO(response.getUsername(), Count.INCREASE, Count.INCREASE));

        LOG.info(String.format(LOG_RESPONSE, String.format(CREATE_FUNCTIONALITY, POST), response));

        return response;
    }

    @Transactional
    public PostDTO updatePost(final PostDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(UPDATE_FUNCTIONALITY, POST), dto));

        Post post = postComponent.getByIdOrException(dto.getId());
        AccessValidator.controlYourData(post.getUsername());
        post.setTitle(dto.getTitle());
        post.setShortDescription(dto.getShortDescription());
        post.setContent(dto.getContent());
        final PostDTO response = postMapper.toDTO(postComponent.save(post));

        LOG.info(String.format(LOG_RESPONSE, String.format(UPDATE_FUNCTIONALITY, POST), response));

        return response;
    }

    @Transactional
    public PostDTO getPostById(final String id) {
        LOG.info(String.format(LOG_REQUEST, String.format(GET_FUNCTIONALITY, POST), id));

        postComponent.throwExceptionIfThePostIsHidden(id);
        Post post = postComponent.getByIdOrException(id);
        post.setViewCount(post.getViewCount() + 1);
        final PostDTO response = postMapper.toDTO(postComponent.save(post));

        LOG.info(String.format(LOG_RESPONSE, String.format(GET_FUNCTIONALITY, POST), response));

        return response;
    }

    public List<CommentDTO> findAllPostComments(final String id) {
        LOG.info(String.format(LOG_REQUEST, COMMENT_FUNCTIONALITY, id));
        postComponent.throwExceptionIfThePostIsHidden(id);
        final List<CommentDTO> response = commentMapper.toDTOs(commentComponent.findAllByPostId(id));
        LOG.info(String.format(LOG_RESPONSE, COMMENT_FUNCTIONALITY, response));

        return response;
    }

    public List<LikeDTO> findAllPostLikes(final String id) {
        LOG.info(String.format(LOG_REQUEST, LIKE_FUNCTIONALITY, id));
        postComponent.throwExceptionIfThePostIsHidden(id);
        final List<LikeDTO> response = likesMapper.toDTOs(likesComponent.findAllPostLikes(id));
        LOG.info(String.format(LOG_RESPONSE, LIKE_FUNCTIONALITY, response));

        return response;
    }

    @Transactional
    public PostDTO patchPostDisplay(final PostDisplayDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(PATCH_FUNCTIONALITY, POST), dto));

        Post post = postComponent.getByIdOrException(dto.getId());
        AccessValidator.controlYourData(post.getUsername());
        boolean isUserCountChangeRequired = post.isDisplay() != dto.isDisplay();
        post.setDisplay(dto.isDisplay());
        final PostDTO response = postMapper.toDTO(postComponent.save(post));
        if (isUserCountChangeRequired) {
            kafkaService.send(USER_TOPIC, new KafkaUserCountDTO(post.getUsername(),
                    post.isDisplay() ? Count.INCREASE : Count.DECREASE, Count.NO_MOVEMENT));
        }

        LOG.info(String.format(LOG_RESPONSE, String.format(PATCH_FUNCTIONALITY, POST), response));

        return response;
    }

    @Transactional
    public List<PostDTO> patchPostsDisplays(final BatchPostDisplayDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(PATCH_FUNCTIONALITY, POST), dto));

        final Map<String, List<PostDisplayDTO>> displayRequests = dto.getRequests()
                .stream()
                .collect(groupingBy(PostDisplayDTO::getId));
        List<Post> posts = postComponent.findAllByIds(displayRequests.keySet().stream().toList());
        posts.forEach(post -> {
            AccessValidator.controlYourData(post.getUsername());
            boolean isUserCountChangeRequired = post.isDisplay() != displayRequests.get(post.getId()).get(0).isDisplay();
            post.setDisplay(displayRequests.get(post.getId()).get(0).isDisplay());

            if (isUserCountChangeRequired) {
                kafkaService.send(USER_TOPIC, new KafkaUserCountDTO(post.getUsername(),
                        post.isDisplay() ? Count.INCREASE : Count.DECREASE, Count.NO_MOVEMENT));
            }
        });
        final List<PostDTO> response = postMapper.toDTOs(postComponent.saveAll(posts));

        LOG.info(String.format(LOG_RESPONSE, String.format(PATCH_FUNCTIONALITY, POST), response));

        return response;
    }

    @Transactional
    public void deletePost(final String id) {
        LOG.info(String.format(LOG_REQUEST, String.format(DELETE_FUNCTIONALITY, POST), id));

        final Post post = postComponent.getByIdOrException(id);
        AccessValidator.controlYourData(post.getUsername());
        postComponent.deleteById(id);
        kafkaService.send(USER_TOPIC, new KafkaUserCountDTO(post.getUsername(), Count.DECREASE, Count.DECREASE));

        LOG.info(String.format(LOG_RESPONSE, String.format(DELETE_FUNCTIONALITY, POST), id));
    }

    @Transactional
    public void deleteByUsername(final String username) {
        LOG.info(String.format(LOG_REQUEST, String.format(DELETE_FUNCTIONALITY, POST), username));
        AccessValidator.controlYourData(username);
        postComponent.deleteByUsername(username);
        LOG.info(String.format(LOG_RESPONSE, String.format(DELETE_FUNCTIONALITY, POST), username));
    }

    public List<PostDTO> searchPosts(final PostSearchRequestDTO searchRequest) {
        LOG.info(String.format(LOG_REQUEST, String.format(SEARCH_FUNCTIONALITY, POST), searchRequest));
        List<PostDTO> response = postComponent.search(new PostSearchRequestWrapper(searchRequest))
                .stream()
                .map(postMapper::toDTO)
                .toList();
        LOG.info(String.format(LOG_RESPONSE, String.format(SEARCH_FUNCTIONALITY, POST), response));

        return response;
    }

}
