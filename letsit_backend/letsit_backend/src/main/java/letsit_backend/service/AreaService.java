package letsit_backend.service;

import letsit_backend.model.Area;
import letsit_backend.repository.AreaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AreaService {

    private final AreaRepository areaRepository;

    public List<Area> getAllRegions() {
        return areaRepository.findByParentId(null);
    }

    public List<Area> getSubRegions(Long parentId) {
        return areaRepository.findByParentId(parentId);
    }

    public Area getRegionByName(String name) {
        return areaRepository.findByNameAndParentIsNull(name);
    }
}
