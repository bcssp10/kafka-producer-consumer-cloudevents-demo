package it.infocert.adapter.message.order;

import it.infocert.domain.Order;
import it.infocert.domain.OrderEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderEventPublisherImpl implements OrderEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventPublisherImpl.class);
    private static final String ORDER_TOPIC = "order-event";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderEventPublisherImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishOrderCreatedEvent(Order order) {
        var orderCreatedEvent = OrderMessageMapper.toOrderCreatedEvent(order);
        var cloudEvent = CloudEventWrapper.wrap(orderCreatedEvent);
        var completableFuture = kafkaTemplate.send(ORDER_TOPIC, order.id().toString(), cloudEvent);
        completableFuture.whenComplete((stringObjectSendResult, throwable) -> {
            if (throwable != null) logger.warn("Filed to publish OrderCreatedEvent: {}", throwable.getMessage());
            else logger.info("Successfully published OrderCreatedEvent: {}", stringObjectSendResult.toString());
        });
    }


}
