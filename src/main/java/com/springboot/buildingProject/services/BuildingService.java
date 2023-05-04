package com.springboot.buildingProject.services;

import com.springboot.buildingProject.models.Building;

public interface BuildingService {
    Building getBuild(Long id);
    Building updateStatus(Building building);

    Building getBuildByFullId(String fullId);
}
