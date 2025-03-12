package it.infocert.adapter.message.order;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.core.format.ContentType;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.PojoCloudEventDataMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import static io.cloudevents.core.CloudEventUtils.mapData;

@Component
public class OrderKafkaListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderKafkaListener.class);

    private static final String ORDER_TOPIC = "order-event";

    // Method to listen for messages from the specified Kafka topic
    @KafkaListener(topics = ORDER_TOPIC, containerFactory = "kafkaListenerContainerFactory")
    public void listen(CloudEvent cloudEvent, Acknowledgment acknowledgment, @Headers MessageHeaders headers) {
        byte[] serialized = EventFormatProvider.getInstance().resolveFormat(ContentType.JSON).serialize(cloudEvent);
        logger.info("Received order message: {}", new String(serialized));

        headers.forEach((key, value) -> {
            if (!key.startsWith("ce_"))
                System.out.printf("  %s : %s%n", key, value);
            else
                System.out.printf("  %s : %s%n", key, new String((byte[]) value));
        });

        OrderCreatedEvent orderCreatedEvent = CloudEventWrapper.unwrap(cloudEvent, OrderCreatedEvent.class);
        acknowledgment.acknowledge();
    }

}