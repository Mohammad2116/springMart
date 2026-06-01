package ir.aspireapps.springmart.dto.cart;

import ir.aspireapps.springmart.dto.product.ProductResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(name = "CartItemResponse", description = "An item inside a shopping cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    @Schema(description = "Product in cart")
    ProductResponse productResponse;
    @Schema(description = "Quantity of this product in the cart", example = "2")
    Long quantity;
}
