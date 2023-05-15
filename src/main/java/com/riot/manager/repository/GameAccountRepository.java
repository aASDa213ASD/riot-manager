package com.riot.manager.repository;

import com.riot.manager.entity.GameAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameAccountRepository extends JpaRepository<GameAccount, Long> {}
