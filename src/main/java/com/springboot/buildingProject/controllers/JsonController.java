package com.springboot.buildingProject.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.postgresql.util.PGobject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/buildings")
public class JsonController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/geojson")
    public Map<String, Object> getBuildingGeoJSON() {
        String sql = "SELECT jsonb_build_object(" +
                "'type', 'FeatureCollection'," +
                "'features', jsonb_agg(features.feature)" +
                ") " +
                "FROM (" +
                "SELECT jsonb_build_object(" +
                "'type', 'Feature'," +
                "'properties', to_jsonb(inputs) - 'id' - 'geom'," +
                "'geometry', ST_AsGeoJSON(geom)::jsonb" +
                ") AS feature " +
                "FROM (SELECT * FROM building3) inputs" +
                ") features";
        return jdbcTemplate.query(sql, new ResultSetExtractor<Map<String, Object>>() {
            @Override
            public Map<String, Object> extractData(ResultSet rs) throws SQLException {
                rs.next();
                PGobject pgObject = (PGobject) rs.getObject(1);
                String jsonString = pgObject.getValue();
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> map = new HashMap<>();
                try {
                    map = objectMapper.readValue(jsonString, Map.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return map;
            }
        });
    }
}