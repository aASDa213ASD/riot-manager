package com.riot.manager.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.riot.manager.dto.*;
import com.riot.manager.service.GameAccountService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/accounts")
public class GameAccountController {
    private final GameAccountService gameAccountService;

    @GetMapping
    public List<GameAccountViewDTO> getAccountList(){
        return gameAccountService.getAccountList();
    }

    @GetMapping("/{id}")
    public GameAccountDTO getRegion(@PathVariable String id) throws NameNotFoundException {
        return gameAccountService.getById(id);
    }

    @PostMapping
    public void createAccount(@RequestBody GameAccountDTO gameAccountDTO) {
        gameAccountService.addAccount(gameAccountDTO);
    }

    @PutMapping("/{id}")
    public void updateAccount(@RequestBody GameAccountEditDTO gameAccountEditDTO) throws JsonProcessingException {
        gameAccountService.updateAccount(gameAccountEditDTO);
    }

    @PostMapping("/{id}/refresh")
    public ResponseEntity<String> changeAccountData(@RequestBody GameAccountEditDTO gameAccountEditDTO) throws NameNotFoundException {
        GameAccountDTO gameAccountDTO = gameAccountService.changeAccountData(gameAccountEditDTO);

        if (gameAccountDTO != null)
            return new ResponseEntity<>("Game account has been updated.", HttpStatus.OK);

        return new ResponseEntity<>("There was an error updating your account.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable("id") Long id){
        gameAccountService.deleteAccount(id);
    }

}
