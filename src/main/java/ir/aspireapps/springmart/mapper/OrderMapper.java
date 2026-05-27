package ir.aspireapps.springmart.mapper;

import ir.aspireapps.springmart.dto.order.OrderItemResponse;
import ir.aspireapps.springmart.dto.order.OrderResponse;
import ir.aspireapps.springmart.model.Order;
import ir.aspireapps.springmart.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface OrderMapper {
    OrderResponse toOrderResponse(Order entity);

    OrderItemResponse toOrderItemResponse(OrderItem entity);
}
