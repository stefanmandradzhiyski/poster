package com.snmi.repository;

import com.snmi.enums.HistoryType;
import com.snmi.model.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History, String> {

    List<History> findByUsernameAndTypeOrderByCreatedAtDesc(final String username, final HistoryType type);

    void deleteByUsernameAndTypeAndPostId(final String username, final HistoryType type, final String postId);

}
