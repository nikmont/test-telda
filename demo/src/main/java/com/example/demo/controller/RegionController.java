package com.example.demo.controller;

import com.example.demo.model.Region;
import com.example.demo.model.RegionDTO;
import com.example.demo.service.RegionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("api/v1/")
public class RegionController {
    private final RegionService regionService;
    @Autowired
    private final ModelMapper modelMapper;

    public RegionController(RegionService regionService, ModelMapper modelMapper) {
        this.regionService = regionService;
        this.modelMapper = modelMapper;
    }

    /**
     * Получение справочника регионов
     *
     * @return Список регионов
     */
    @GetMapping("region/all")
    public ResponseEntity<List<RegionDTO>> getRegions() {
        List<Region> all = regionService.getAll();

        if (all.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(Collections.emptyList());
        }

        return ResponseEntity.status(HttpStatus.OK).body(all.stream().map(e -> modelMapper.map(e, RegionDTO.class)).toList());
    }

    /**
     * Получение региона по id
     *
     * @param id Уникальный идентификатор региона
     * @return Регион
     */
    @GetMapping("region/{id}")
    public ResponseEntity<RegionDTO> getRegion(@PathVariable Long id) {
        Region region = regionService.get(id);

        if (region != null) {
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(region, RegionDTO.class));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Добавление региона
     *
     * @param regionDto добавляемый регион
     * @return Идентификатор региона в базе данных
     */
    @PostMapping("region")
    public ResponseEntity<Region> addRegion(@RequestBody RegionDTO regionDto) {

        if (regionDto == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Region region = modelMapper.map(regionDto, Region.class);
        Region addedRegion = regionService.add(region);

        if (addedRegion == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();

        return ResponseEntity.status(HttpStatus.OK).body(addedRegion);
    }

    /**
     * Изменение региона
     *
     * @param id        Уникальный идентификатор региона
     * @param regionDto обновленный объект региона
     * @return Идентификатор региона в базе данных
     */
    @PutMapping("region/{id}")
    public ResponseEntity<RegionDTO> editRegion(@PathVariable Long id, @RequestBody RegionDTO regionDto) {
        Region region = modelMapper.map(regionDto, Region.class);
        Region edit = regionService.edit(id, region);

        if (edit != null) {
            return ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(edit, RegionDTO.class));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Удаление региона
     *
     * @param id Уникальный идентификатор региона
     * @return Статус операции
     */
    @DeleteMapping("region/{id}")
    public ResponseEntity removeRegion(@PathVariable Long id) {
        boolean delete = regionService.delete(id);

        if (!delete) {
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).build();
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
