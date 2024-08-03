package letsit_backend.controller;

import letsit_backend.model.Area;
import letsit_backend.service.AreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/areas")
@RequiredArgsConstructor
public class AreaController {

    private final AreaService areaService;

    @GetMapping
    public List<Area> getAllRegions() {
        return areaService.getAllRegions();
    }

    @GetMapping("/{regionId}/subareas")
    public List<Area> getSubRegions(@PathVariable Long regionId) {
        return areaService.getSubRegions(regionId);
    }
}
