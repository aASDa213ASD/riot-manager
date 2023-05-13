package com.riot.manager.service;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.dto.RegionEditDTO;
import com.riot.manager.entity.Region;
import com.riot.manager.entity.User;
import com.riot.manager.mapper.RegionMapper;
import com.riot.manager.repository.RegionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.NameNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    public List<RegionDTO> getRegionList() {
        List<Region> serviceEntities = regionRepository.findAll();
        return serviceEntities.stream()
                .map(regionMapper::toDTO)
                .toList();
    }

    public RegionDTO addRegion(RegionDTO regionDTO) {
        boolean regionExists = regionRepository.existsByRegionNameAndUserId(regionDTO.getRegionName(), regionDTO.getUserId());
        if (regionExists)
            throw new IllegalStateException("Region with the same name already exists for this user");

        Region region = regionMapper.toEntity(regionDTO);
        region.setRegionName(regionDTO.getRegionName());
        region.setUserId(regionDTO.getUserId());

        return regionMapper.toDTO(regionRepository.save(region));
    }
    
    public RegionDTO getById(String id) throws NameNotFoundException {
        Long longId = Long.parseLong(id);

        Optional<Region> oRegion = regionRepository.findById(longId);

        if(oRegion.isEmpty()){
            throw new NameNotFoundException("Couldn't find any region by specified id");
        }

        return regionMapper.toDTO(oRegion.get());
    }

    public RegionDTO changeRegionName(RegionEditDTO regiondata) {
        Long regionId = regiondata.getId();
        Optional<Region> oRegion = Objects.requireNonNull(regionRepository.findById(regionId));
        Region regionEntity = oRegion.orElseThrow(() -> new IllegalArgumentException("Region not found"));

        regionEntity.setRegionName(regiondata.getRegionName());
        return regionMapper.toDTO(regionRepository.save(regionEntity));
    }

    public void deleteRegion(Long id) {
        Region existingRegion = regionRepository.findById(id).orElseThrow(() -> new IllegalStateException(
                new Throwable("Region with id " + id + " does not exist".formatted()))
        );
        regionRepository.deleteById(id);
    }
}
