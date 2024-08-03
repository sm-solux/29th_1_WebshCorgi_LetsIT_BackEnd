package letsit_backend.dto.portfolio;

import letsit_backend.model.ProjectPortfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AiPortfolioRequestDto {
    //private String prtTitle;
    private String workDescription;
    private String issues;
    private String solutions;
    private String feedback;

    public AiPortfolioRequestDto(ProjectPortfolio prt) {
        //this.prtTitle = prt.getPrtTitle();
        this.workDescription = prt.getWorkDescription();
        this.issues = prt.getIssues();
        this.solutions = prt.getSolutions();
        this.feedback = prt.getFeedback();
    }
}
