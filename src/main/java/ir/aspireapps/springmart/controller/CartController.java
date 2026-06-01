package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Shopping Cart",
        description = """
                            Shopping cart management endpoints: 
                            add/remove products, update quantities, fetch cart contents, 
                            calculate totals (discounts, taxes, shipping), 
                            and prepare cart for checkout.
                            """)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/user/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(
            summary = "Clear cart",
            description = "Remove all products from cart"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Request Accepted"
    )
    @DeleteMapping("/clear")
    public ResponseEntity<CartResponse> clear(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.clear(userDetails.user()));
    }

    @Operation(
            summary = "Get Cart",
            description = "Get current cart details, and all items in it."
    )
    @ApiResponse(
            responseCode = "201",
            description = "Cart Returned"
    )
    @GetMapping()
    public ResponseEntity<CartResponse> get(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.OK).body(
                cartService.get(userDetails.user()));
    }

    @Operation(
            summary = "Add product",
            description = "Add a product to cart"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Request Accepted"
    )
    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> add(@Positive @PathVariable long productId,
                                            @Positive @PathVariable long quantity,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.add(userDetails.user(), productId, quantity));
    }

    @Operation(
            summary = "Remove product",
            description = "remove the number of items in quantity from product in cart"
    )
    @ApiResponse(
            responseCode = "202",
            description = "Request Accepted"
    )
    @DeleteMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> remove(@Positive @PathVariable long productId,
                                               @Positive @PathVariable long quantity,
                                               @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.remove(userDetails.user(), productId, quantity));
    }

    @Operation(
            summary = "Checkout",
            description = "Convert current cart to an order"
    )
    @ApiResponse(
            responseCode = "201",
            description = "Order created"
    )
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> toOrder(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cartService.createOrder(userDetails.user())
        );
    }
}

