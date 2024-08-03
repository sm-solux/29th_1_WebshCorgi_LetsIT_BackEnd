package letsit_backend.dto.team;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeamMemberLoadInfoDto {
    private Long userId;
    private String userName;
    private String position;
    private String ProfileImageUrl;
}
