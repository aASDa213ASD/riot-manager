package com.riot.manager.mapper;

import com.riot.manager.dto.UserDTO;
import com.riot.manager.dto.UserViewDTO;
import com.riot.manager.entity.User;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    UserViewDTO toViewDTO(User user);

    User toEntity(UserDTO userDTO);
}
