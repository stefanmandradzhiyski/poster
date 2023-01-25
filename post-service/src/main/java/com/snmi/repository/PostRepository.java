package com.snmi.repository;

import com.snmi.model.Post;
import com.snmi.projection.PostProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends BasePostRepository<Post> {

    void deleteByUsername(final String username);

    @Query(
        """
        SELECT CASE WHEN COUNT(p) = 0 THEN FALSE ELSE TRUE END
        FROM Post AS p
        WHERE p.id = :postId AND (p.username = :username OR p.display = TRUE)
        """
    )
    boolean isPostDisplayed(final String postId, final String username);

    @Query(
        """
        SELECT
            p.id AS id,
            p.title AS title,
            p.shortDescription AS shortDescription,
            p.username AS username,
            p.overallRating AS overallRating,
            p.createdAt AS createdAt
        FROM Post AS p
        WHERE
            (:title = '' OR LOWER(p.title) LIKE CONCAT('%', LOWER(:title), '%'))
            AND (:username = '' OR LOWER(p.username) LIKE CONCAT('%', LOWER(:username), '%'))
            AND (:minimumOverallRating = 0 OR p.overallRating >= :minimumOverallRating)
            AND ((:inactiveIncluded = TRUE) OR (:inactiveIncluded = FALSE AND p.display = TRUE))
        ORDER BY
            CASE WHEN :sortType = 'CREATION_DATE' AND :sortDirection = 'ASC' THEN createdAt END ASC,
            CASE WHEN :sortType = 'CREATION_DATE' AND :sortDirection = 'DESC' THEN createdAt END DESC,
            CASE WHEN :sortType = 'RATING' AND :sortDirection = 'ASC' THEN overallRating END ASC,
            CASE WHEN :sortType = 'RATING' AND :sortDirection = 'DESC' THEN overallRating END DESC
        """
    )
    List<PostProjection> search(
        @Param("title") String title,
        @Param("username") String username,
        @Param("minimumOverallRating") int minimumOverallRating,
        @Param("inactiveIncluded") boolean inactiveIncluded,
        @Param("sortType") String sortType,
        @Param("sortDirection") String sortDirection
    );

}
