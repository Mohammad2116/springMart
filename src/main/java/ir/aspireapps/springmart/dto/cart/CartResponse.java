package ir.aspireapps.springmart.dto.cart;

import ir.aspireapps.springmart.dto.user.UserResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(name = "CartResponse", description = "Shopping cart returned to the client")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    @Schema(description = "Cart id", example = "5")
    Long id;
    @Schema(description = "Cart owner details")
    UserResponse user;
    @Schema(description = "List of items in the cart")
    List<CartItemResponse> items;
}
