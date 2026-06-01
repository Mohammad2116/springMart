package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
            summary = "Clear",
            description = """
                    Remove all products from cart
                    
                    - ADMIN or USER role required
                    """
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
            summary = "Get",
            description = """
                    Get current cart details, and all items of it.
                    
                    - ADMIN or USER role required
                    """
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
            description = """
                    Add a product to cart.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "202",
            description = "Request Accepted"
    )
    @PutMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> add(
            @Parameter(
                    description = "ID of the product to add to cart",
                    example = "5"
            )
            @Positive @PathVariable long productId,
            @Parameter(
                    description = "Quantity of target product to add to the cart",
                    example = "2"
            )
            @Positive @PathVariable long quantity,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.add(userDetails.user(), productId, quantity));
    }

    @Operation(
            summary = "Remove product",
            description = """
                    remove some or all of quantities of a product in the cart
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "202",
            description = "Request Accepted"
    )
    @DeleteMapping("/{productId}/{quantity}")
    public ResponseEntity<CartResponse> remove(
            @Parameter(
                    description = "ID of the product to remove from the cart",
                    example = "5"
            )
            @Positive @PathVariable long productId,
            @Parameter(
                   description = "Number of the product to remove from the cart",
                    example = "2"
            )
            @Positive @PathVariable long quantity,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(
                cartService.remove(userDetails.user(), productId, quantity));
    }

    @Operation(
            summary = "Checkout",
            description = """
                    Checkout the current cart and open an order based of it.
                    """
    )
    @ApiResponse(
            responseCode = "201",
            description = "OrderResponse fo the created order"
    )
    @PostMapping("/checkout")
    public ResponseEntity<OrderResponse> toOrder(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                cartService.createOrder(userDetails.user())
        );
    }
}

