package com.snmi.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snmi.dto.KafkaUserCountDTO;
import com.snmi.service.KafkaRouter;
import com.snmi.service.UserService;
import jakarta.validation.Validator;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RouteService extends KafkaRouter {

    private final UserService userService;

    public RouteService(
        Validator validator,
        UserService userService,
        ObjectMapper objectMapper
    ) {
        super.setLOG(LoggerFactory.getLogger(RouteService.class));
        super.setPARSE_ERROR("Kafka user consumer ended up with a JSON parse error: %s");
        super.setObjectMapper(objectMapper);
        super.setValidator(validator);
        this.userService = userService;
    }

    @Override
    public void route(final ConsumerRecord<String, Object> consumerRecord) {
        final KafkaUserCountDTO dto = parseJSON(consumerRecord.value().toString(), KafkaUserCountDTO.class);
        if (dto == null) {
            return;
        }
        validate(dto);
        userService.managePostCounters(dto);
    }

}
