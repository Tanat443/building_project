package com.springboot.buildingProject.repositories;

import com.springboot.buildingProject.models.Building;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface BuildingRepository extends JpaRepository<Building, Long> {

    Building findBuildingByFullId(String fullId);
}