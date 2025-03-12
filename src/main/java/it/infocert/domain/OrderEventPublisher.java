package it.infocert.domain;

public interface OrderEventPublisher {
    void publishOrderCreatedEvent(Order order);
}
