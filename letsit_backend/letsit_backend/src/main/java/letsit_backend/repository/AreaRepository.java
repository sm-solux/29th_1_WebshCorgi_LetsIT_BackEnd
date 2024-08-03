package letsit_backend.repository;

import letsit_backend.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AreaRepository extends JpaRepository<Area, Long> {
    Area findByNameAndParentIsNull(String name);
    List<Area> findByParentId(Long parentId);
}
