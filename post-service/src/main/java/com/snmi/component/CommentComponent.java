package com.snmi.component;

import com.snmi.exception.ConflictException;
import com.snmi.model.Comment;
import com.snmi.repository.BasePostRepository;
import com.snmi.repository.CommentRepository;
import com.snmi.util.QueryParamUtil;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.snmi.constants.GlobalConstants.CONFLICT_INTERACTION;

@Component
public class CommentComponent extends BaseComponent<Comment> implements UniqueInteraction {

    private final CommentRepository commentRepository;

    public CommentComponent(final BasePostRepository<Comment> genericRepository, final CommentRepository commentRepository) {
        super(Comment.class, genericRepository);
        this.commentRepository = commentRepository;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<Comment> findAllByPostId(final String id) {
        return commentRepository.findAllByPostId(QueryParamUtil.getStringOrEmpty(id));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void throwExceptionIfTheInteractionAlreadyExists(final String username, final String postId) {
        if (commentRepository.existsByUsernameAndPostId(
                QueryParamUtil.getStringOrEmpty(username),
                QueryParamUtil.getStringOrEmpty(postId)
        )) {
            throw new ConflictException(String.format(CONFLICT_INTERACTION, "comment", postId, username));
        }
    }

}
