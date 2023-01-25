package com.snmi.component;

import com.snmi.exception.NotFoundException;
import com.snmi.model.Post;
import com.snmi.projection.PostProjection;
import com.snmi.repository.BasePostRepository;
import com.snmi.repository.PostRepository;
import com.snmi.util.AccessValidator;
import com.snmi.util.CollectionUtil;
import com.snmi.util.QueryParamUtil;
import com.snmi.wrapper.PostSearchRequestWrapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Component
public class PostComponent extends BaseComponent<Post> {

    private final PostRepository postRepository;

    public PostComponent(final BasePostRepository<Post> genericRepository, final PostRepository postRepository) {
        super(Post.class, genericRepository);
        this.postRepository = postRepository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Post> saveAll(final List<Post> posts) {
        return CollectionUtil.isEmpty(posts) ? Collections.emptyList() : postRepository.saveAll(posts);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<PostProjection> search(final PostSearchRequestWrapper searchRequestWrapper) {
        return postRepository.search(
            searchRequestWrapper.getTitle(),
            searchRequestWrapper.getUsername(),
            searchRequestWrapper.getMinimumOverallRating(),
            searchRequestWrapper.isInactiveIncluded(),
            searchRequestWrapper.getSortType(),
            searchRequestWrapper.getSortDirection()
        );
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Post> findAllByIds(final List<String> ids) {
        return postRepository.findAllById(QueryParamUtil.getListOrSingleton(ids));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteByUsername(final String username) {
        postRepository.deleteByUsername(QueryParamUtil.getStringOrEmpty(username));
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void throwExceptionIfThePostIsHidden(final String postId) {
        if (!postRepository.isPostDisplayed(
                QueryParamUtil.getStringOrEmpty(postId),
                QueryParamUtil.getStringOrEmpty(AccessValidator.getLoggedUsername())
        )) {
            throw new NotFoundException(Post.class, postId);
        }
    }

}
