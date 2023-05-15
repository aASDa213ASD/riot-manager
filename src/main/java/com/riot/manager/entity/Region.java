package com.riot.manager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "regions")
public class Region {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "regions_generator")
    @SequenceGenerator(name="regions_generator", sequenceName = "regions_seq", allocationSize=1)
    private Long id;

    @Column(name = "region_name")
    private String regionName;

    @JoinColumn(name = "user_id")
    private Long userId;

    @OneToMany(mappedBy = "regionId")
    private List<GameAccount> gameAccountsList;
}
