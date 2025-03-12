package it.infocert.adapter.rest.order;

import it.infocert.domain.Order;

class OrderRestMapper {

    public static Order toOrder(OrderDto orderDto) {
        return new Order(orderDto.id(), orderDto.item(), orderDto.quantity());
    }

    public static OrderDto toOrderDTO(Order order) {
        return new OrderDto(order.id(), order.item(), order.quantity());
    }

}
