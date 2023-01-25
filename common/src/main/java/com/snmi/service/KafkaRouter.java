package com.snmi.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snmi.exception.UnProcessableEntityException;
import com.snmi.util.CollectionUtil;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;

import java.util.Set;
import java.util.stream.Collectors;

import static com.snmi.constants.GlobalConstants.DELIMITER;

@Setter
public abstract class KafkaRouter {

    private Logger LOG;
    private String PARSE_ERROR;
    private Validator validator;
    private ObjectMapper objectMapper;

    protected abstract void route(final ConsumerRecord<String, Object> consumerRecord);

    protected <T> T parseJSON(final String JSON, Class<T> clazz) {
        try {
            return objectMapper.readValue(JSON, clazz);
        } catch (Exception e) {
            LOG.error(String.format(PARSE_ERROR, JSON));
            return null;
        }
    }

    protected <T> void validate(T dto) {
        Set<ConstraintViolation<T>> violations = validator.validate(dto);
        if (CollectionUtil.isNotEmpty(violations)) {
            throw new UnProcessableEntityException(violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.joining(DELIMITER))
            );
        }
    }

}
