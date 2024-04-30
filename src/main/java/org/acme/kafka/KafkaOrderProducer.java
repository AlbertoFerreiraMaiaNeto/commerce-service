package org.acme.kafka;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
@Slf4j
public class KafkaOrderProducer {

    @Inject
    @Channel("orders-out")
    Emitter<KafkaOrderDTO> emitter;

    public void publish(KafkaOrderDTO kafkaOrderDTO) {
        try{
            this.emitter.send(kafkaOrderDTO);
            log.info("Sent message to Kafka, Order Owner: {}", kafkaOrderDTO.getOrderDTO().getOrderOwner());
        } catch (Exception e) {
            log.error("Error to trying send message to Kafka, Order Owner: {}", kafkaOrderDTO.getOrderDTO().getOrderOwner());
        }
    }

}