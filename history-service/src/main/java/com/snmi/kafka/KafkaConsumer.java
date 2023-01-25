package com.snmi.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.snmi.constants.KafkaGlobalConstants.HISTORY_TOPIC;

@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final RouteService routeService;

    @KafkaListener(topics = HISTORY_TOPIC, containerFactory = "kafkaServiceCommunicationListener")
    public void consume(ConsumerRecord<String, Object> consumerRecord) {
        routeService.route(consumerRecord);
    }

}
