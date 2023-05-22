package com.riot.manager.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.riot.manager.dto.*;
import com.riot.manager.entity.GameAccount;
import com.riot.manager.entity.Region;
import com.riot.manager.mapper.GameAccountMapper;
import com.riot.manager.mapper.RegionMapper;
import com.riot.manager.repository.GameAccountRepository;
import com.riot.manager.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class GameAccountService {
    private final GameAccountRepository gameAccountRepository;
    private final GameAccountMapper gameAccountMapper;
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    private final String APIKey = "RGAPI-5b6d28b3-532c-4683-8121-324399d50571";

    private String requestSummonerData(GameAccount gameAccount, String region)
    {
        RestTemplate restTemplate = new RestTemplate();

        String summonerName = gameAccount.getName();

        String URL = "https://" + region.toLowerCase() + "1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + APIKey;
        String response = restTemplate.getForEntity(URL, String.class).getBody();
        return response;
    }

    private String requestRankedData(String ID, String region)
    {
        RestTemplate restTemplate = new RestTemplate();

        String URL = "https://" + region.toLowerCase() + "1.api.riotgames.com/lol/league/v4/entries/by-summoner/" + ID + "?api_key=" + APIKey;
        String response = restTemplate.getForEntity(URL, String.class).getBody();
        return response;
    }

    @Cacheable("accountsCache")
    public List<GameAccountViewDTO> getAccountList()
    {
        List<GameAccount> serviceEntities = gameAccountRepository.findAll();
        return serviceEntities.stream()
                .map(gameAccountMapper::toViewDTO)
                .toList();
    }

    public GameAccountDTO addAccount(GameAccountDTO gameAccountDTO)
    {
        GameAccount gameAccount = gameAccountMapper.toEntity(gameAccountDTO);
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccount));
    }

    @CacheEvict("accountsCache")
    public GameAccountDTO updateAccount(GameAccountEditDTO gameAccountData) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();

        Long gameAccId = gameAccountData.getId();
        Optional<GameAccount> oGameAccount = Objects.requireNonNull(gameAccountRepository.findById(gameAccId));
        GameAccount gameAccount = oGameAccount.orElseThrow(() -> new IllegalArgumentException("Account not found"));

        Optional<Region> oRegion = regionRepository.findById(gameAccount.getRegionId());
        RegionDTO regionDTO = regionMapper.toDTO(oRegion.get());

        String region = regionDTO.getRegionName();
        String tier = "Unranked";
        String rank = "";

        // Get account's Summoner data, level and accountId for Ranked Data
        String jsonSummonerData = this.requestSummonerData(gameAccount, region);
        Map<String, Object> jsonSummonerDataMap = objectMapper.readValue(jsonSummonerData, new TypeReference<Map<String, Object>>() {});
        String id = (String) jsonSummonerDataMap.get("id");
        int level = (int) jsonSummonerDataMap.get("summonerLevel");

        // Get Ranked Data from AccountID, actual rank, tier, win/losses, PTS, etc.

        String jsonRankedData = this.requestRankedData(id, region);
        if (!jsonRankedData.isEmpty())
        {
            List<Map<String, Object>> data = objectMapper.readValue(jsonRankedData, List.class);
            if (!data.isEmpty()) {
                Map<String, Object> leagueData = data.get(0);

                tier = (String) leagueData.get("tier");
                rank = (String) leagueData.get("rank");
            }
        }

        gameAccount.setLevel(level);
        gameAccount.setRank(tier + " " + rank);
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccount));
    }

    @Cacheable("accountsCache")
    public GameAccountDTO getById(String id) throws NameNotFoundException
    {
        Long longId = Long.parseLong(id);

        Optional<GameAccount> oGameAccount = gameAccountRepository.findById(longId);

        if(oGameAccount.isEmpty()){
            throw new NameNotFoundException("Couldn't find any account by specified id");
        }

        return gameAccountMapper.toDTO(oGameAccount.get());
    }

    public GameAccountDTO changeAccountData(GameAccountEditDTO gameAccountData)
    {
        Long gameAccId = gameAccountData.getId();
        Optional<GameAccount> oGameAccount = Objects.requireNonNull(gameAccountRepository.findById(gameAccId));
        GameAccount gameAccountEntity = oGameAccount.orElseThrow(() -> new IllegalArgumentException("Account not found"));

        gameAccountEntity.setLevel(gameAccountData.getLevel());
        gameAccountEntity.setRank(gameAccountData.getRank());
        gameAccountEntity.setName(gameAccountData.getName());
        gameAccountEntity.setLogin(gameAccountData.getLogin());
        return gameAccountMapper.toDTO(gameAccountRepository.save(gameAccountEntity));
    }

    public void deleteAccount(Long id)
    {
        GameAccount existingAccount = gameAccountRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                new Throwable("Account with id " + id + " does not exist".formatted()))
        );
        gameAccountRepository.deleteById(id);
    }
}
