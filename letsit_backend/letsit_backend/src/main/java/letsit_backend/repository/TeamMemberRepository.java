package letsit_backend.repository;

import letsit_backend.model.Member;
import letsit_backend.model.TeamMember;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    List<TeamMember> findAllByTeamId(TeamPost teamPost);
    Optional<TeamMember> findByTeamIdAndUserId(TeamPost teamPost, Member member);
    List<TeamMember> findByTeamId_TeamId(Long teamId);
    List<TeamMember> findAllByUserId(Member member);
    Optional<TeamMember> findTeamMemberByTeamIdAndTeamMemberRole(TeamPost teamPost, TeamMember.Role role);


}
