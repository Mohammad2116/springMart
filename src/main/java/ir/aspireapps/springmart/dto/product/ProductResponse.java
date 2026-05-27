package ir.aspireapps.springmart.dto.product;


import ir.aspireapps.springmart.model.Category;

import java.math.BigDecimal;

public record ProductResponse(
        Long id,
        String name,
        String description,
        Long stock,
        BigDecimal price,
        Category category
) {
}
