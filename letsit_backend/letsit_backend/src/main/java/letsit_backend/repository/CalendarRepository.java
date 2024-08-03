package letsit_backend.repository;

import letsit_backend.model.Calendar;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {
    List<Calendar> findAllByTeamId(TeamPost teamPost);
}
