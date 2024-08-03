package letsit_backend.dto.team;


import jakarta.validation.constraints.NotBlank;
import letsit_backend.model.TeamPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TeamUpdateRequestDto {
    @NotBlank(message = "팀명을 입력하세요.")
    private String teamName;
    private String notionLink;
    private String githubLink;
    // private Member changeLeader;

    @Builder
    public TeamUpdateRequestDto(String teamName, String notionLink, String githubLink) {
        this.teamName = teamName;
        this.notionLink = notionLink;
        this.githubLink = githubLink;
    }

}
