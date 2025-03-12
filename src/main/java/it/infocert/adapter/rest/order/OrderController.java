package it.infocert.adapter.rest.order;

import it.infocert.domain.OrderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderUseCase) {
        this.orderService = orderUseCase;
    }

    @PostMapping
    public void createOrder(@RequestBody OrderDto orderDto) {
        var order = OrderRestMapper.toOrder(orderDto);
        orderService.createOrder(order);
    }

    @GetMapping("/{id}")
    public OrderDto getOrder(@PathVariable Long id) {
        var order =  orderService.getOrderById(id);
        return OrderRestMapper.toOrderDTO(order);
    }
}