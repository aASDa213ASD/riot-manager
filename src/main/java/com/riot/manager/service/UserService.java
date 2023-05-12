package com.riot.manager.service;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.dto.UserDTO;
import com.riot.manager.dto.UserViewDTO;
import com.riot.manager.entity.User;
import com.riot.manager.mapper.RegionMapper;
import com.riot.manager.mapper.UserMapper;
import com.riot.manager.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public UserViewDTO getById(String id) throws NameNotFoundException {
        Long longId = Long.parseLong(id);

        Optional<User> oUser = userRepository.findById(longId);

        if(oUser.isEmpty()){
            throw new NameNotFoundException("Couldn't find any user by specified id");
        }

        return userMapper.toViewDTO(oUser.get());
    }

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);

        boolean userExists = userRepository.existsByUsername(userDTO.getUsername());
        if (userExists)
            throw new IllegalStateException("This username has already been taken.");

        return userMapper.toDTO(userRepository.save(user));
    }

    public UserDTO changePWD(UserDTO userdata) {
        String username = userdata.getUsername();
        Optional<User> oUser = Objects.requireNonNull(userRepository.findByUsername(username));
        User userEntity = oUser.orElseThrow(() -> new IllegalArgumentException("User not found"));

        userEntity.setPassword(userdata.getPassword());
        return userMapper.toDTO(userRepository.save(userEntity));
    }

    public void deleteUser(Long id) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                new Throwable("User with id " + id + " does not exist".formatted()))
        );
        userRepository.deleteById(id);
    }
}
