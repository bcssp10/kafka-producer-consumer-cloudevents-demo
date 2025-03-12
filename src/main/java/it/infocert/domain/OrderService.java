package it.infocert.domain;

public interface OrderService {

    void createOrder(Order order);

    Order getOrderById(Long id);

}
