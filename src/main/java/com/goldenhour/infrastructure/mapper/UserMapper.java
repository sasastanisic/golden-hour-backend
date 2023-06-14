package com.goldenhour.infrastructure.mapper;

import com.goldenhour.domain.user.entity.User;
import com.goldenhour.domain.user.model.UserRequestDTO;
import com.goldenhour.domain.user.model.UserResponseDTO;
import com.goldenhour.domain.user.model.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(UserRequestDTO userRequestDTO);

    UserResponseDTO toUserResponseDTO(User user);

    void updateUserFromDTO(UserUpdateDTO userUpdateDTO, @MappingTarget User user);

}
