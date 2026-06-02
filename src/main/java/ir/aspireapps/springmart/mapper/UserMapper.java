package ir.aspireapps.springmart.mapper;

import ir.aspireapps.springmart.dto.user.UserRegistrationRequest;
import ir.aspireapps.springmart.dto.user.UserResponse;
import ir.aspireapps.springmart.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserMapper {
    @Mapping(target = "lockedBy", ignore = true)
    @Mapping(target = "lockedAt", ignore = true)
    @Mapping(target = "locked", ignore = true)
    @Mapping(target = "lockReason", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    @Mapping(target = "cart", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "passwordUpdatedAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deletedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    User toEntity(UserRegistrationRequest request);

    UserResponse toResponse(User entity);

}
