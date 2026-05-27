package ir.aspireapps.springmart.service;

import ir.aspireapps.springmart.dto.cart.CartResponse;
import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.error.EmptyCartException;
import ir.aspireapps.springmart.error.InsufficientProductStcok;
import ir.aspireapps.springmart.error.ResourceNotFoundException;
import ir.aspireapps.springmart.mapper.CartMapper;
import ir.aspireapps.springmart.mapper.OrderMapper;
import ir.aspireapps.springmart.model.*;
import ir.aspireapps.springmart.repo.CartRepository;
import ir.aspireapps.springmart.repo.OrderRepository;
import ir.aspireapps.springmart.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Transactional
    public CartResponse clear(User user) {
        Cart cart = prepareUserCart(user);
        cart.clear();
        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse get(User user) {
        Cart cart = prepareUserCart(user);
        return cartMapper.toResponse(cart);
    }

    @Transactional
    public CartResponse add(User user, long productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id: " + productId + " not found"));
        if (product.getStock() < quantity) {
            throw new InsufficientProductStcok("Insufficient stock for product Id: " + productId);
        }
        product.setStock(product.getStock() - quantity);
        Cart cart = prepareUserCart(user);
        cart.add(product, quantity);
        return cartMapper.toResponse(cart);
    }

    private Cart prepareUserCart(User user) {
        Cart cart;
        cart = cartRepository.findByUserId(user.getId())
                .orElseGet(() -> {
                    Cart c = new Cart();
                    c.setUser(user);
                    return cartRepository.save(c);
                });
        return cart;
    }

    @Transactional
    public CartResponse remove(User user, long productId, long quantity) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Id: " + productId + " not found"));
        product.setStock(product.getStock() + quantity);
        Cart cart = prepareUserCart(user);
        cart.remove(product, quantity);
        return cartMapper.toResponse(cart);
    }

    @Transactional
    public OrderResponse createOrder(User user) {
        Cart cart = prepareUserCart(user);
        if (cart.getProducts().isEmpty())
            throw new EmptyCartException("Can't create order from an empty cart");
        Order order = new Order();
        user.addOrder(order);
        Map<Product, Long> products = cart.getProducts();
        products.forEach((product, quantity) -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(product.getId());
            orderItem.setProductName(product.getName());
            orderItem.setQuantity(quantity);
            order.addItem(orderItem);
            order.setTotal(order.getTotal().add(product.getPrice().multiply(BigDecimal.valueOf(quantity))));
            cart.getProducts().remove(product);
        });
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toOrderResponse(savedOrder);
    }
}
