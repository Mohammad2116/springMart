package ir.aspireapps.springmart.controller;

import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/payed/{id}")
    public ResponseEntity<OrderResponse> payed(@Valid @PathVariable long id,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.payed(userDetails.user(), id));
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(@Valid @PathVariable long id,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.cancel(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/ship/{id}")
    public ResponseEntity<Void> ship(@Valid @PathVariable long id,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.ship(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    public ResponseEntity<List<OrderResponse>> listOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAll(userDetails.user()));
    }

    public ResponseEntity<List<OrderResponse>> listOrdersConfirmed(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllConfirmed(userDetails.user()));
    }

    public ResponseEntity<List<OrderResponse>> listOrdersPayed(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllPayed(userDetails.user()));
    }

    public ResponseEntity<List<OrderResponse>> listOrdersShipped(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllShip(userDetails.user()));
    }

    public ResponseEntity<List<OrderResponse>> listOrdersCancelled(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllCanceled(userDetails.user()));
    }
}
