package com.snmi.component;

import com.snmi.enums.HistoryType;
import com.snmi.model.History;
import com.snmi.repository.HistoryRepository;
import com.snmi.util.QueryParamUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HistoryComponent {

    private final HistoryRepository historyRepository;

    @Transactional(propagation = Propagation.MANDATORY)
    public History save(final History entity) {
        return entity == null ? null : historyRepository.save(entity);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public List<History> findByUsernameAndType(final String username, final HistoryType type) {
        return historyRepository.findByUsernameAndTypeOrderByCreatedAtDesc(QueryParamUtil.getStringOrEmpty(username), type);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteByUsernameTypeAndPostId(final String username, final HistoryType type, final String postId) {
        historyRepository.deleteByUsernameAndTypeAndPostId(
            QueryParamUtil.getStringOrEmpty(username),
            type,
            QueryParamUtil.getStringOrEmpty(postId)
        );
    }

}
