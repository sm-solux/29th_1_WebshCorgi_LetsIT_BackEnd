package letsit_backend.repository;

import letsit_backend.model.Member;
import letsit_backend.model.TeamEvaluation;
import letsit_backend.model.TeamMember;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamEvaluationRepository extends JpaRepository<TeamEvaluation, Long> {
    List<TeamEvaluation> findAllByTeamIdAndEvaluator(TeamPost teamPost, Member evaluator);
    boolean existsByTeamIdAndEvaluatorAndEvaluatee(TeamPost teamPost, Member evaluator, Member evaluatee);
    List<TeamEvaluation> findAllByEvaluatee(Member evaluatee);
}
