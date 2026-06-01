package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.mapper.OrderMapper;
import ir.aspireapps.springmart.model.Order;
import ir.aspireapps.springmart.model.OrderState;
import ir.aspireapps.springmart.model.Product;
import ir.aspireapps.springmart.model.User;
import ir.aspireapps.springmart.repo.OrderRepository;
import ir.aspireapps.springmart.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ProductRepository productRepository;

    @Transactional
    public OrderResponse paid(User user, long id) {
        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order id: " + id + " not found"));

        order.setState(OrderState.PAYED);
        return orderMapper.toOrderResponse(order);
    }

    @Transactional
    public void cancel(User user, long id) {
        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order id: " + id + " not found"));

        order.getItems().forEach(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product id: " + item.getProductId() + " not found"));
            product.setStock(product.getStock() + item.getProductId());
        });


        order.setState(OrderState.CANCELED);
        orderRepository.delete(order);
    }

    public void ship(User user, long id) {
        Order order = orderRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Order id: " + id + " not found"));

        order.setState(OrderState.SHIPPED);
    }

    public List<OrderResponse> getAll(User user) {
        return orderRepository
                .findAllByUserId(user.getId())
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getAllConfirmed(User user) {
        return orderRepository
                .findAllByUserIdAndState(user.getId(), OrderState.CONFIRMED)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getAllPayed(User user) {
        return orderRepository
                .findAllByUserIdAndState(user.getId(), OrderState.PAYED)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getAllShip(User user) {
        return orderRepository
                .findAllByUserIdAndState(user.getId(), OrderState.SHIPPED)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    public List<OrderResponse> getAllCanceled(User user) {
        return orderRepository
                .findAllByUserIdAndState(user.getId(), OrderState.CANCELED)
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }
}
