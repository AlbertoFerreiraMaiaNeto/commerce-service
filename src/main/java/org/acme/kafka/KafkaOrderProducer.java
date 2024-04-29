package org.acme.kafka;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.acme.persistence.dto.KafkaOrderDTO;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@ApplicationScoped
public class KafkaOrderProducer {

    @Inject
    @Channel("orders-out")
    Emitter<KafkaOrderDTO> emitter;

    public void publish(KafkaOrderDTO kafkaOrderDTO) {
        this.emitter.send(kafkaOrderDTO);
    }

}