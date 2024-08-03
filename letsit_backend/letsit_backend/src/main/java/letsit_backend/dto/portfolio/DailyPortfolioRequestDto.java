package letsit_backend.dto.portfolio;

import letsit_backend.model.Member;
import letsit_backend.model.ProjectPortfolio;
import letsit_backend.model.TeamPost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DailyPortfolioRequestDto {

    private Long teamId;
    private Long userId;
    private String prtTitle;
    private String workDescription;
    private String issues;
    private String solutions;
    private String feedback;

    public ProjectPortfolio toEntity(TeamPost teampost, Member member) {
        return ProjectPortfolio.builder()
                .teamId(teampost)
                .userId(member)
                .prtTitle(prtTitle)
                .workDescription(workDescription)
                .issues(issues)
                .solutions(solutions)
                .feedback(feedback)
                .build();
    }
}
