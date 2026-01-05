package com.lmt.ecommerce.laptophub.mapper;

import com.lmt.ecommerce.laptophub.dto.request.UserRegisterRequest;
import com.lmt.ecommerce.laptophub.dto.response.UserResponse;
import com.lmt.ecommerce.laptophub.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orders", ignore = true)
    @Mapping(target = "password", ignore = true)
    User toUser(UserRegisterRequest request);
}
