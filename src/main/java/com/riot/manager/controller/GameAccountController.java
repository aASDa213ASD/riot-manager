package com.riot.manager.controller;

import com.riot.manager.dto.GameAccountDTO;
import com.riot.manager.service.GameAccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class GameAccountController {
    private final GameAccountService gameAccountService;

    @PostMapping
    public void createRegion(@RequestBody GameAccountDTO gameAccountDTO) {
        gameAccountService.addAccount(gameAccountDTO);
    }
}
