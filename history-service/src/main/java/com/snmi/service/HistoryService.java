package com.snmi.service;

import com.snmi.component.HistoryComponent;
import com.snmi.dto.HistoryDTO;
import com.snmi.dto.KafkaHistoryDTO;
import com.snmi.enums.HistoryType;
import com.snmi.mapper.HistoryMapper;
import com.snmi.model.History;
import com.snmi.repository.HistoryRepository;
import com.snmi.util.AccessValidator;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.snmi.constants.GlobalConstants.CREATE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.DELETE_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.GET_FUNCTIONALITY;
import static com.snmi.constants.GlobalConstants.LOG_REQUEST;
import static com.snmi.constants.GlobalConstants.LOG_RESPONSE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

    private static final Logger LOG = LoggerFactory.getLogger(HistoryService.class);

    private static final String HISTORY = "history";
    private static final long HISTORY_CLEAN_DELAY_12H = 43200000;

    private final HistoryMapper historyMapper;
    private final HistoryComponent historyComponent;
    private final HistoryRepository historyRepository;

    @Transactional
    public void createHistory(final KafkaHistoryDTO dto) {
        LOG.info(String.format(LOG_REQUEST, String.format(CREATE_FUNCTIONALITY, HISTORY), dto));
        final History response = historyComponent.save(historyMapper.toModel(dto));
        LOG.info(String.format(LOG_RESPONSE, String.format(CREATE_FUNCTIONALITY, HISTORY), response));
    }

    public List<HistoryDTO> findByUsernameAndType(final String username, final HistoryType type) {
        LOG.info(String.format(LOG_REQUEST, GET_FUNCTIONALITY, HISTORY));
        AccessValidator.controlYourData(username);
        final List<HistoryDTO> response = historyMapper.toDTOs(historyComponent.findByUsernameAndType(username, type));
        LOG.info(String.format(LOG_REQUEST, GET_FUNCTIONALITY, HISTORY));

        return response;
    }

    @Transactional
    public void deleteHistory(final KafkaHistoryDTO dto) {
        LOG.info(String.format(LOG_REQUEST, DELETE_FUNCTIONALITY, HISTORY));
        historyComponent.deleteByUsernameTypeAndPostId(dto.getUsername(), dto.getType(), dto.getPostId());
        LOG.info(String.format(LOG_RESPONSE, DELETE_FUNCTIONALITY, HISTORY));
    }

    @Scheduled(fixedRate = HISTORY_CLEAN_DELAY_12H)
    protected void clearHistoryTable() {
        historyRepository.deleteAll();
    }

}
