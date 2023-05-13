package com.riot.manager.controller;

import com.riot.manager.dto.RegionDTO;
import com.riot.manager.dto.RegionEditDTO;
import com.riot.manager.service.RegionService;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.NameNotFoundException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/regions")
public class RegionController {
    private final RegionService regionService;

    @GetMapping
    public List<RegionDTO> getRegionList(){
        return regionService.getRegionList();
    }

    @GetMapping("/{id}")
    public RegionDTO getRegion(@PathVariable String id) throws NameNotFoundException {
        return regionService.getById(id);
    }

    @PostMapping
    public void createRegion(@RequestBody RegionDTO regionDTO) {
        regionService.addRegion(regionDTO);
    }

    @PutMapping
    public ResponseEntity<String> changeRegionName(@RequestBody RegionEditDTO region) throws NameNotFoundException {
        RegionDTO regionDTO = regionService.changeRegionName(region);

        if (regionDTO != null) {
            return new ResponseEntity<>("Region name has been updated.", HttpStatus.OK);
        }

        return new ResponseEntity<>("There was an error updating your password.", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Long id){
        regionService.deleteRegion(id);
    }
}
