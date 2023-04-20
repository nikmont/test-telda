package com.example.demo.service;

import com.example.demo.Repository.RegionRepository;
import com.example.demo.model.Region;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = {"regs"})
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;

    public RegionServiceImpl(RegionRepository regionRepository) {
        this.regionRepository = regionRepository;
    }

    @Override
    @Cacheable(cacheNames = {"regs"})
    public List<Region> getAll() {

        return regionRepository.findAll();
    }

    @Override
    @Cacheable(cacheNames = {"regs"}, key = "#regId")
    public Region get(Long regId) {

        return regionRepository.findById(regId);
    }

    @Override
    @CacheEvict(cacheNames = {"regs"}, key = "#region.id", allEntries = true)
    public Region add(Region region) {
        Long insert = regionRepository.insert(region);

        if (insert == 0) {
            return null;
        }

        return regionRepository.findById(regionRepository.getLastId());
    }

    @Override
    @CacheEvict(cacheNames = {"regs"}, key = "#id", allEntries = true)
    public Region edit(Long id, Region region) {
        int update = regionRepository.update(id, region);

        if (update == 0) {
            return null;
        }

        return regionRepository.findById(id);
    }

    @Override
    @CacheEvict(cacheNames = {"regs"}, key = "#id", allEntries = true)
    public boolean delete(Long id) {
        int delete = regionRepository.delete(id);

        return delete == 1;
    }
}
