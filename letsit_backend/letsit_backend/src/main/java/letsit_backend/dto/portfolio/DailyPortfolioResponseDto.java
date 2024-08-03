package letsit_backend.dto.portfolio;

import letsit_backend.model.ProjectPortfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class DailyPortfolioResponseDto {
    private Long teamId;
    private Long prtId;
    private String prtTitle;
    private Timestamp prtCreateDate;
    private Timestamp prtUpdateDate;
    private String workDescription;
    private String issues;
    private String solutions;
    private String feedback;

    public DailyPortfolioResponseDto(ProjectPortfolio prt) {
        this.teamId = prt.getTeamId().getTeamId();
        this.prtId = prt.getPrtId();
        this.prtTitle = prt.getPrtTitle();
        this.prtCreateDate = prt.getPrtCreateDate();
        this.prtUpdateDate = prt.getPrtUpdateDate();
        this.workDescription = prt.getWorkDescription();
        this.issues = prt.getIssues();
        this.solutions = prt.getSolutions();
        this.feedback = prt.getFeedback();
    }
}
