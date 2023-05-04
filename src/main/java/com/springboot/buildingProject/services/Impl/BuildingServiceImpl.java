package com.springboot.buildingProject.services.Impl;

import com.springboot.buildingProject.models.Building;
import com.springboot.buildingProject.repositories.BuildingRepository;
import com.springboot.buildingProject.services.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BuildingServiceImpl implements BuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Override
    public Building getBuild(Long id) {
        return buildingRepository.getReferenceById(id);
    }

    @Override
    public Building updateStatus(Building building) {
        return buildingRepository.save(building);
    }

    @Override
    public Building getBuildByFullId(String fullId) {
        return buildingRepository.findBuildingByFullId(fullId);
    }

}
