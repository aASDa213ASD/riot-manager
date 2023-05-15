package com.riot.manager.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "game_accounts")
public class GameAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gameacc_generator")
    @SequenceGenerator(name = "gameacc_generator", sequenceName = "gameacc_seq", allocationSize = 1)
    private Long id;

    @NonNull
    @JoinColumn(name = "region_id")
    private Long regionId;

    @Column
    private String login;

    @Column
    private String password;

    @NonNull
    @Column
    private String name;

    @Column
    private int level;

    @Column
    private String rank;
}
