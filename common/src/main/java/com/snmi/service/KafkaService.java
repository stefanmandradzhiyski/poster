package com.snmi.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.snmi.exception.UnProcessableEntityException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import static com.snmi.constants.GlobalConstants.KAFKA_SEND_FUNCTIONALITY;

@Service
@RequiredArgsConstructor
public class KafkaService {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaService.class);

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void send(final String topic, final Object object) {
        final String JSONObject = serializeTheObject(object);
        LOG.info(String.format(KAFKA_SEND_FUNCTIONALITY, JSONObject, topic));
        kafkaTemplate.send(topic, JSONObject);
    }

    private String serializeTheObject(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new UnProcessableEntityException(e.getMessage());
        }
    }

}
