package ir.aspireapps.springmart.dto.cart;

import ir.aspireapps.springmart.dto.user.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartResponse {
    Long id;
    UserResponse user;
    List<CartItemResponse> items;
}
