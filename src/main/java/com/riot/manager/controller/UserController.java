package com.riot.manager.controller;

import com.riot.manager.dto.UserDTO;
import com.riot.manager.dto.UserViewDTO;
import com.riot.manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    // TODO: Remove PWD from ViewDTO
    @GetMapping
    public List<UserViewDTO> getUserList(){
        return userService.getUserList();
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }
}
