package com.snmi.repository;

import com.snmi.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends BasePostRepository<Comment> {

    List<Comment> findAllByPostId(final String id);

    boolean existsByUsernameAndPostId(final String username, final String postId);

}
