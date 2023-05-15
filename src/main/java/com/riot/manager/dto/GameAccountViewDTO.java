package com.riot.manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GameAccountViewDTO {
    private Long regionId;
    private String name;
    private int level;
    private String rank;
}
