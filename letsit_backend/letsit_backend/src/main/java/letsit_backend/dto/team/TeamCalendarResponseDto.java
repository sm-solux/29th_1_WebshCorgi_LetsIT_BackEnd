package letsit_backend.dto.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class TeamCalendarResponseDto {
    private Long calendarId;
    private String title;
    private String description;
    private String startDate;
    private String endDate;
}
