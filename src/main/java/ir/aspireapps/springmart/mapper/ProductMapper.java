package ir.aspireapps.springmart.mapper;

import ir.aspireapps.springmart.dto.product.ProductRegisterRequest;
import ir.aspireapps.springmart.dto.product.ProductResponse;
import ir.aspireapps.springmart.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface ProductMapper {

    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "category", ignore = true)
    Product toEntity(ProductRegisterRequest request);

    ProductResponse toResponse(Product product);
}
