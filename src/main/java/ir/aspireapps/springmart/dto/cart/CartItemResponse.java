package ir.aspireapps.springmart.dto.cart;

import ir.aspireapps.springmart.dto.product.ProductResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponse {
    ProductResponse productResponse;
    Long quantity;
}
