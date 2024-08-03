package letsit_backend.dto.team;

import lombok.Getter;

@Getter
public class TeamCalendarRequestDto {
    private String title;
    private String description;
    private String startDate;
    private String endDate;
}
