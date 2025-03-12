package it.infocert.adapter.jdbc.order;

import it.infocert.domain.Order;
import it.infocert.domain.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryImpl implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryImpl(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order save(Order order) {
        var orderEntity = OrderJdbcMapper.toOrderEntity(order);
        var savedOrderEntity = orderJpaRepository.save(orderEntity);
        return OrderJdbcMapper.toOrder(savedOrderEntity);
    }

    @Override
    public Order findById(Long id) {
        return orderJpaRepository.findById(id)
                .map(OrderJdbcMapper::toOrder)
                .orElse(null);
    }
}
