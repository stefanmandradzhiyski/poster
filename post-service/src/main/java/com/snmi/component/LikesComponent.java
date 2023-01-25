package com.snmi.component;

import com.snmi.exception.ConflictException;
import com.snmi.model.Likes;
import com.snmi.repository.BasePostRepository;
import com.snmi.repository.LikesRepository;
import com.snmi.util.QueryParamUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.snmi.constants.GlobalConstants.CONFLICT_INTERACTION;

@Component
public class LikesComponent extends BaseComponent<Likes> implements UniqueInteraction {

    private final LikesRepository likesRepository;

    public LikesComponent(final BasePostRepository<Likes> genericRepository, final LikesRepository likesRepository) {
        super(Likes.class, genericRepository);
        this.likesRepository = likesRepository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Likes> findAllPostLikes(final String id) {
        return likesRepository.findAllByPostId(QueryParamUtil.getStringOrEmpty(id));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void throwExceptionIfTheInteractionAlreadyExists(final String username, final String postId) {
        if (likesRepository.existsByUsernameAndPostId(
                QueryParamUtil.getStringOrEmpty(username),
                QueryParamUtil.getStringOrEmpty(postId)
        )) {
            throw new ConflictException(String.format(CONFLICT_INTERACTION, "like", postId, username));
        }
    }

}
