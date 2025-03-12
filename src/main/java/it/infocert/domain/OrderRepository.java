package it.infocert.domain;

public interface OrderRepository {
    Order save(Order order);
    Order findById(Long id);
}
