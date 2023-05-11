package com.riot.manager.service;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.entity.Region;
import com.riot.manager.mapper.RegionMapper;
import com.riot.manager.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public RegionDTO addRegion(RegionDTO regionDTO) {
        boolean regionExists = regionRepository.existsByRegionNameAndUserId(regionDTO.getRegion_name(), regionDTO.getUser_id());
        if (regionExists)
            throw new IllegalStateException("Region with the same name already exists for this user");

        Region region = regionMapper.toEntity(regionDTO);
        region.setRegionName(regionDTO.getRegion_name());
        region.setUserId(regionDTO.getUser_id());

        return regionMapper.toDTO(regionRepository.save(region));
    }
}
