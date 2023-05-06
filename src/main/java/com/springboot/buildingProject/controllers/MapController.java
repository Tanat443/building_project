package com.springboot.buildingProject.controllers;

import com.springboot.buildingProject.enums.Role;
import com.springboot.buildingProject.models.BuildJson;
import com.springboot.buildingProject.models.Building;
import com.springboot.buildingProject.models.User;
import com.springboot.buildingProject.services.BuildJsonService;
import com.springboot.buildingProject.services.BuildingService;
import com.springboot.buildingProject.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MapController {

    @Autowired
    private UserService userService;

    @Autowired
    private BuildingService buildingService;

    @Autowired
    private BuildJsonService buildJsonService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PreAuthorize("isAnonymous()")
    @GetMapping(value = "/login")
    public String signInPage() {
        return "login";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "/signup")
    public String signUpPage() {
        return "signup";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @PostMapping(value = "/signup")
    public String addUser(User user) {

        user.setActive(true);
        user.getRoles().add(Role.ROLE_USER);
        System.out.println(user);
        userService.createUser(user);
        return "signup";
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @GetMapping(value = "admin")
    public String adminPage(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/")
    public String homePage(Model model) {
        String sql1 = "SELECT COUNT(*) FROM building3";
        String sql2 = "SELECT COUNT(*) FROM building3 where status='yes'";
        String sql3 = "SELECT COUNT(*) FROM building3 where status='no'";

        int countAllBuildings = jdbcTemplate.queryForObject(sql1, Integer.class);
        int countYesBuildings = jdbcTemplate.queryForObject(sql2, Integer.class);
        int countNoBuildings = jdbcTemplate.queryForObject(sql3, Integer.class);
        model.addAttribute("countAllBuildings", countAllBuildings);
        model.addAttribute("countYesBuildings", countYesBuildings);
        model.addAttribute("countNoBuildings", countNoBuildings);
        return "home";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/map")
    public String map(Model model) {
        BuildJson buildJson = buildJsonService.getJsonBuildings();
        model.addAttribute("buildJson", buildJson);
        return "map";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "/changeStatusYes")
    public String changeStatusYes(@RequestParam(name = "id", defaultValue = "0") String fullId) {
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
