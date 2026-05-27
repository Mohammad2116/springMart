package ir.aspireapps.springmart.controller;

import ir.aspireapps.springmart.dto.cart.CartResponse;
import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.security.CustomUserDetails;
import ir.aspireapps.springmart.service.CartService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @PutMapping("/clear")
    public ResponseEntity<CartResponse> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.clear(userDetails.user()));
    }

    @GetMapping()
    public ResponseEntity<CartResponse> get(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
                cartService.get(userDetails.user()));
    }

    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> add(@Positive @PathVariable long productId,
                                            @Positive @PathVariable long quantity,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.add(userDetails.user(), productId, quantity));
    }

    @DeleteMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> remove(@Positive @PathVariable long productId,
                                               @Positive @PathVariable long quantity,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.remove(userDetails.user(), productId, quantity));
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderResponse> toOrder(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cartService.createOrder(userDetails.user())
        );
    }
}

