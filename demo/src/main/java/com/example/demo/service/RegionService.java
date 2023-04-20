package com.example.demo.service;

import com.example.demo.model.Region;

import java.util.List;

public interface RegionService {

    List<Region> getAll();

    Region get(Long id);

    Region add(Region region);

    Region edit(Long id, Region region);

    boolean delete(Long id);
}
