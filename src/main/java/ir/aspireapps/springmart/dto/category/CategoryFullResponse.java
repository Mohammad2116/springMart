package ir.aspireapps.springmart.dto.category;

import ir.aspireapps.springmart.dto.product.ProductResponse;

import java.util.List;

public record CategoryFullResponse(
        Long id,
        String name,
        List<ProductResponse> products
) {
}
