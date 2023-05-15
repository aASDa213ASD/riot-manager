package com.riot.manager.service;

import com.riot.manager.dto.*;
import com.riot.manager.entity.GameAccount;
import com.riot.manager.entity.Region;
import com.riot.manager.mapper.GameAccountMapper;
import com.riot.manager.repository.GameAccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameAccountService {
    private final GameAccountRepository gameAccountRepository;
    private final GameAccountMapper gameAccountMapper;

    public List<GameAccountViewDTO> getAccountList() {
        List<GameAccount> serviceEntities = gameAccountRepository.findAll();
        return serviceEntities.stream()
                .map(gameAccountMapper::toViewDTO)
                .toList();
    }

    public GameAccountDTO addAccount(GameAccountDTO gameAccountDTO) {
        GameAccount gameAccount = gameAccountMapper.toEntity(gameAccountDTO);
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccount));
    }

    public GameAccountDTO getById(String id) throws NameNotFoundException {
        Long longId = Long.parseLong(id);

        Optional<GameAccount> oGameAccount = gameAccountRepository.findById(longId);

        if(oGameAccount.isEmpty()){
            throw new NameNotFoundException("Couldn't find any account by specified id");
        }

        return gameAccountMapper.toDTO(oGameAccount.get());
    }

    public GameAccountDTO changeAccountData(GameAccountEditDTO gameAccountData) {
        Long gameAccId = gameAccountData.getId();
        Optional<GameAccount> oGameAccount = Objects.requireNonNull(gameAccountRepository.findById(gameAccId));
        GameAccount gameAccountEntity = oGameAccount.orElseThrow(() -> new IllegalArgumentException("Account not found"));

        gameAccountEntity.setLevel(gameAccountData.getLevel());
        gameAccountEntity.setRank(gameAccountData.getRank());
        gameAccountEntity.setName(gameAccountData.getName());
        gameAccountEntity.setLogin(gameAccountData.getLogin());
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccountEntity));
    }

    public void deleteAccount(Long id) {
        GameAccount existingAccount = gameAccountRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                new Throwable("Account with id " + id + " does not exist".formatted()))
        );
        gameAccountRepository.deleteById(id);
    }
}
