package letsit_backend.dto.portfolio;

import letsit_backend.model.ProjectPortfolio;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.sql.Timestamp;
@Getter
@AllArgsConstructor
public class DailyPortfolioListDto {
    private Long teamId;
    private Long prtId;
    private String prtTitle;
    private Timestamp prtCreateDate;

    public DailyPortfolioListDto(ProjectPortfolio prt) {
        this.teamId = prt.getTeamId().getTeamId();
        this.prtId = prt.getPrtId();
        this.prtTitle = prt.getPrtTitle();
        this.prtCreateDate = prt.getPrtCreateDate();
    }
}
