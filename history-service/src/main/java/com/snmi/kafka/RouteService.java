package com.snmi.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snmi.dto.KafkaHistoryDTO;
import com.snmi.enums.Count;
import com.snmi.service.HistoryService;
import com.snmi.service.KafkaRouter;
import jakarta.validation.Validator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RouteService extends KafkaRouter {

    private final HistoryService historyService;

    public RouteService(
        Validator validator,
        HistoryService historyService,
        ObjectMapper objectMapper
    ) {
        super.setLOG(LoggerFactory.getLogger(RouteService.class));
        super.setPARSE_ERROR("Kafka history consumer ended up with a JSON parse error: %s");
        super.setObjectMapper(objectMapper);
        super.setValidator(validator);
        this.historyService = historyService;
    }

    @Override
    public void route(final ConsumerRecord<String, Object> consumerRecord) {
        final KafkaHistoryDTO dto = parseJSON(consumerRecord.value().toString(), KafkaHistoryDTO.class);
        if (dto == null) {
            return;
        }
        validate(dto);

        if (Count.INCREASE == dto.getAction()) {
            historyService.createHistory(dto);
        } else {
            historyService.deleteHistory(dto);
        }
    }

}
