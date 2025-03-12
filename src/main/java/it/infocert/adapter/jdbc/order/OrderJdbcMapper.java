package it.infocert.adapter.jdbc.order;

import it.infocert.domain.Order;

class OrderJdbcMapper {

    public static Order toOrder(OrderEntity orderEntity) {
        return new Order(orderEntity.id(), orderEntity.item(), orderEntity.quantity());
    }

    public static OrderEntity toOrderEntity(Order order) {
        return new OrderEntity(order.id(), order.item(), order.quantity());
    }

}