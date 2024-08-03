package letsit_backend.repository;

import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.model.ProjectPortfolio;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<ProjectPortfolio, Long> {
    List<ProjectPortfolio> findAllByTeamIdAndUserId(TeamPost team, Member member);
}
