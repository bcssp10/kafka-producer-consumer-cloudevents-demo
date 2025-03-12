package it.infocert.adapter.message.order;

import it.infocert.domain.Order;

class OrderMessageMapper {

    public static Order toOrder(OrderCreatedEvent orderCreatedEvent) {
        return new Order(orderCreatedEvent.id());
    }

    public static OrderCreatedEvent toOrderCreatedEvent(Order order) {
        return new OrderCreatedEvent(order.id());
    }
}
