package com.snmi.repository;

import com.snmi.model.Likes;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends BasePostRepository<Likes> {

    List<Likes> findAllByPostId(final String id);

    boolean existsByUsernameAndPostId(final String username, final String postId);

}
