package com.riot.manager.repository;

import com.riot.manager.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegionRepository extends JpaRepository<Region, Long> {
    boolean existsByRegionNameAndUserId(@Param("region_name") String region_name, @Param("user_id") Long user_id);
}
