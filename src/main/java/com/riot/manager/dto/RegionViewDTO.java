package com.riot.manager.dto;

import com.riot.manager.entity.GameAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegionViewDTO {
    private String regionName;
    private Long userId;
    private List<GameAccount> gameAccountsList;
}
