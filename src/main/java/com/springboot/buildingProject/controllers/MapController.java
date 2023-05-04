package com.springboot.buildingProject.controllers;

import com.springboot.buildingProject.models.BuildJson;
import com.springboot.buildingProject.models.Building;
import com.springboot.buildingProject.services.BuildJsonService;
import com.springboot.buildingProject.services.BuildingService;
import com.springboot.buildingProject.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MapController {
    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/login")
    public String signInPage(){
        return "login";
    };

    @Autowired
    private UserService userService;
    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildJsonService buildJsonService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/map")
    public String map(Model model) {
        BuildJson buildJson = buildJsonService.getJsonBuildings();
        model.addAttribute("buildJson", buildJson);

        System.out.println(userService.getCurrentUser().getRoles());
        return "map";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/changeStatusYes")
    public String changeStatusYes(HttpServletRequest request,
                                  @RequestParam(name = "id", defaultValue = "0") String fullId) {
        Building building = buildingService.getBuildByFullId(fullId);
        if (building != null) {
            building.setStatus("yes");
            buildingService.updateStatus(building);
        }

        return "redirect:/map";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/changeStatusNo")
    public String changeStatusNo(@RequestParam(name = "id", defaultValue = "0") String fullId) {
        Building building = buildingService.getBuildByFullId(fullId);
        if (building != null) {
            building.setStatus("no");
            buildingService.updateStatus(building);
        }
        return "redirect:/map";
    }
}
