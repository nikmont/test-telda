package com.example.demo.model;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class RegionDTO {

    private Long id;

    private String name;

    private String shortName;
}
