package com.snmi.repository;

import com.snmi.model.User;
import com.snmi.projection.UserProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    void deleteByUsername(final String username);

    boolean existsByUsername(final String username);

    Optional<User> findByUsername(final String username);

    Optional<User> findByUsernameAndActiveTrue(final String username);

    @Query(value =
        """
        SELECT u.id AS id, u.username AS username, u.visiblePostCount AS postCount
        FROM User AS u
        WHERE (LOWER(:username) = '' OR LOWER(u.username) LIKE CONCAT('%', LOWER(:username), '%')) AND u.active = TRUE
        ORDER BY
            CASE WHEN :sortType = 'USERNAME' AND :sortDirection = 'ASC' THEN username END ASC,
            CASE WHEN :sortType = 'USERNAME' AND :sortDirection = 'DESC' THEN username END DESC,
            CASE WHEN :sortType = 'POSTS_COUNT' AND :sortDirection = 'ASC' THEN visiblePostCount END ASC,
            CASE WHEN :sortType = 'POSTS_COUNT' AND :sortDirection = 'DESC' THEN visiblePostCount END DESC
        """
    )
    List<UserProjection> search(
        @Param("username") final String username,
        @Param("sortType") final String sortType,
        @Param("sortDirection") final String sortDirection
    );

}
