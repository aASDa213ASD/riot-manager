package com.riot.manager.service;

import com.riot.manager.dto.GameAccountDTO;
import com.riot.manager.entity.GameAccount;
import com.riot.manager.mapper.GameAccountMapper;
import com.riot.manager.repository.GameAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GameAccountService {
    private final GameAccountRepository gameAccountRepository;
    private final GameAccountMapper gameAccountMapper;

    public GameAccountDTO addAccount(GameAccountDTO gameAccountDTO) {
        GameAccount gameAccount = gameAccountMapper.toEntity(gameAccountDTO);
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccount));
    }
}
