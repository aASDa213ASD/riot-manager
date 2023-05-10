package com.riot.manager.controller;

import com.riot.manager.dto.UserDTO;
import com.riot.manager.dto.UserViewDTO;
import com.riot.manager.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserViewDTO> getUserList(){
        return userService.getUserList();
    }

    @GetMapping("/{id}")
    public UserViewDTO getUser(@PathVariable String id) throws NameNotFoundException {
        return userService.getById(id);
    }

    @PostMapping
    public void createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
    }

    @PutMapping
    public ResponseEntity<String> changePWD(@RequestBody UserDTO user) throws NameNotFoundException {
        UserDTO userDTO = userService.changePWD(user);

        if (userDTO != null) {
            return new ResponseEntity<>("Password has been updated.", HttpStatus.OK);
        }

        return new ResponseEntity<>("There was an error updating your password.", HttpStatus.BAD_REQUEST);
    }
}
