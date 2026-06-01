package ir.aspireapps.springmart.controller;

import io.swagger.v3.oas.annotations.Operation;
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
            summary = "Set to paid",
            description = "Set the status of a order to paid"
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/paid/{id}")
    public ResponseEntity<OrderResponse> pay(@Valid @PathVariable long id,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.paid(userDetails.user(), id));
    }

    @Operation(
            summary = "Cancel Order",
            description = "Set the status of a order to CANCELED - and convert it to as a calneled" +
                    " Order, Only CREATED and PAYID orders can be canceled."
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelOrder(@Valid @PathVariable long id,
                                            @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.cancel(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "Ship Order",
            description = "Set the status of a order to SHIPPED - and convert it to as a shiped" +
                    " Order, Only PAYID orders can be set to SHIPPED."
    )
    @ApiResponse(
            responseCode = "200",
            description = "order state changed successfully"
    )
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/ship/{id}")
    public ResponseEntity<Void> ship(@Valid @PathVariable long id,
                                     @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.ship(userDetails.user(), id);
        return ResponseEntity.accepted().build();
    }

    @Operation(
            summary = "List Order",
            description = "Retrieve list of all orders set by user in any state."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderResponse>> listOrders(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAll(userDetails.user()));
    }

    @Operation(
            summary = "List Order",
            description = "Retrieve list of all orders set by user at CONFIRMED state."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    public ResponseEntity<List<OrderResponse>> listOrdersConfirmed(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllConfirmed(userDetails.user()));
    }

    @Operation(
            summary = "List Order",
            description = "Retrieve list of all orders set by user at PAID state."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    public ResponseEntity<List<OrderResponse>> listOrdersPayed(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllPayed(userDetails.user()));
    }

    @Operation(
            summary = "List Order",
            description = "Retrieve list of all orders set by user at SHIPPED state."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    public ResponseEntity<List<OrderResponse>> listOrdersShipped(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllShip(userDetails.user()));
    }

    @Operation(
            summary = "List Order",
            description = "Retrieve list of all orders set by user at CANCELED state."
    )
    @ApiResponse(
            responseCode = "200",
            description = "List returned"
    )
    public ResponseEntity<List<OrderResponse>> listOrdersCancelled(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(orderService.getAllCanceled(userDetails.user()));
    }
}
