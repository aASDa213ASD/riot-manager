package com.riot.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionEditDTO {
    private Long id;
    private String regionName;
    private Long userId;
}
