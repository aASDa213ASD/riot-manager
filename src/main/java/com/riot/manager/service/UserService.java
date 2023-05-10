package com.riot.manager.service;

import com.riot.manager.dto.UserDTO;
import com.riot.manager.dto.UserViewDTO;
import com.riot.manager.entity.User;
import com.riot.manager.mapper.UserMapper;
import com.riot.manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserViewDTO> getUserList() {
        List<User> serviceEntities = userRepository.findAll();
        return serviceEntities.stream()
                .map(userMapper::toViewDTO)
                .toList();
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        return userMapper.toDTO(userRepository.save(user));
    }
}
