package ir.aspireapps.springmart.mapper;

import ir.aspireapps.springmart.dto.cart.CartItemResponse;
import ir.aspireapps.springmart.dto.cart.CartResponse;
import ir.aspireapps.springmart.model.Cart;
import ir.aspireapps.springmart.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        uses = {ProductMapper.class})

public abstract class CartMapper {
    @Autowired
    protected ProductMapper productMapper;

    @Mapping(target = "items", source = "products")
    public abstract CartResponse toResponse(Cart entity);

    public List<CartItemResponse> mapProductsMapToList(Map<Product, Long> products) {
        if (products == null || products.isEmpty())
            return Collections.emptyList();

        return products.entrySet().stream()
                .map(entry -> {
                    CartItemResponse item = new CartItemResponse();
                    item.setProductResponse(productMapper.toResponse(entry.getKey()));
                    item.setQuantity(entry.getValue());
                    return item;
                }).collect(Collectors.toList());
    }
}
