package letsit_backend.dto.team;

import jakarta.validation.constraints.NotBlank;
import letsit_backend.model.Member;
import lombok.Getter;

import java.util.List;
@Getter
public class TeamCreateDto {

    @NotBlank(message = "팀명을 입력해주세요")
    private String teamName;

    private String githubLink;
    private String notionLink;
}
