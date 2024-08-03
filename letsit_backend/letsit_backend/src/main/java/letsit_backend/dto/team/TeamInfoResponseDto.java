package letsit_backend.dto.team;

import letsit_backend.model.TeamMember;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class TeamInfoResponseDto {
    private String teamName;
    private String notionLink;
    private String githubLink;
    private List<TeamMemberLoadInfoDto> teamMemberInfo;
}
