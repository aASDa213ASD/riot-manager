package com.riot.manager.mapper;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.entity.Region;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RegionMapper {
    RegionDTO toDTO(Region region);
    Region toEntity(RegionDTO regionDTO);
}
