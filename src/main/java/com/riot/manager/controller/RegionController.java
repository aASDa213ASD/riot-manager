package com.riot.manager.controller;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.service.RegionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    @PostMapping
    public void createRegion(@RequestBody RegionDTO regionDTO) {
        regionService.addRegion(regionDTO);
    }
}
