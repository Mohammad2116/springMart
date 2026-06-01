package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(
        name = "Orders",
        description = """
                            APIs for managing customer orders — 
                            place orders, view details, update status, 
                            list history, and track shipments.
                            """)
@SecurityRequirement(name = "Bearer Authentication")
@RestController
@RequestMapping("/api/v1/order")
@CrossOrigin
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @Operation(
            summary = "Paid",
            description = """
                    Set the status of an order as PAID
                    
                    - ADMIN role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/paid/{id}")
    public ResponseEntity<OrderResponse> paid(
            @Parameter(
                    description = "Id of the order who paid",
                    example = "5"
            )
            @Valid @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.paid(userDetails.user(), id));
    }

    @Operation(
            summary = "Cancel",
            description = """
                    Set the status of an order to CANCELED
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(
            @Parameter(
                    description = "Id of the order who canceled",
                    example = "5"
            )
            @Valid @PathVariable long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.cancel(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Shipped",
            description = """
                    Set the status of an order to SHIPPED
                    
                    - ADMIN role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/ship/{id}")
    public ResponseEntity<Void> ship(
            @Parameter(
                    description = "Id of the order who shipped",
                    example = "5"
            )
            @Valid @PathVariable long id,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.ship(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "List orders",
            description = """
                    Retrieve a list of all orders set by user at any state.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping()
    public ResponseEntity<List<OrderResponse>> listOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAll(userDetails.user()));
    }

    @Operation(
            summary = "List confirmed orders",
            description = """
                    Retrieve a list of all orders set by user at CONFIRMED state.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of confirmed orders returns"
    )
    @GetMapping("/confirmed")
    public ResponseEntity<List<OrderResponse>> listOrdersConfirmed(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllConfirmed(userDetails.user()));
    }

    @Operation(
            summary = "List paid orders",
            description = """
                    Retrieve a list of all orders set by user at PAID state.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of paid orders returns"
    )
    @GetMapping("/paid")
    public ResponseEntity<List<OrderResponse>> listOrdersPaid(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllPayed(userDetails.user()));
    }

    @Operation(
            summary = "List shipped orders",
            description = """
                    Retrieve a list of all orders set by user at SHIPPED state.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of shipped orders returns"
    )
    @GetMapping("/shipped")
    public ResponseEntity<List<OrderResponse>> listOrdersShipped(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllShip(userDetails.user()));
    }

    @Operation(
            summary = "List canceled orders",
            description = """
                    Retrieve a list of all orders set by user at CANCELED state.
                    
                    - ADMIN or USER role required
                    """
    )
    @ApiResponse(
            responseCode = "200",
            description = "List of canceled orders returns"
    )
    @GetMapping("/cancelled")
    public ResponseEntity<List<OrderResponse>> listOrdersCancelled(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllCanceled(userDetails.user()));
    }
}
