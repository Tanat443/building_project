package com.springboot.buildingProject.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity(name = "building3")
@Data
public class Building {

    @Id
    @Column(name = "id")
    private Integer id;


    @Column(name = "full_id")
    private String fullId;

    @Column(name = "osm_id")
    private String osmId;

    @Column(name = "addr:street")
    private String addrStreet;

    @Column(name = "addr:housenumber")
    private String addrHouseNumber;

    @Column(name = "addr:city")
    private String addrCity;

    @Column(name = "status")
    private String status;

}
