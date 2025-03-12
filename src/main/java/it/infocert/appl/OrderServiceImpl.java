package it.infocert.appl;

import it.infocert.domain.Order;
import it.infocert.domain.OrderEventPublisher;
import it.infocert.domain.OrderRepository;
import it.infocert.domain.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher eventPublisher;

    public OrderServiceImpl(OrderRepository orderRepository, OrderEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void createOrder(Order order) {
        var savedOrder = orderRepository.save(order);
        eventPublisher.publishOrderCreatedEvent(savedOrder);
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
