package ir.aspireapps.springmart.repo;

import ir.aspireapps.springmart.dto.order.OrderItemResponse;
import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.model.Order;
import ir.aspireapps.springmart.model.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByIdAndUserId(long id, UUID userId);

    List<Order> findAllByUserId(UUID id);

    List<Order> findAllByUserIdAndState(UUID userId, OrderState state);
}
