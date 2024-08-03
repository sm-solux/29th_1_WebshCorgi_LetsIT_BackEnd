package letsit_backend.repository;

import letsit_backend.model.Post;
import letsit_backend.model.TeamPost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamPostRepository extends JpaRepository<TeamPost, Long> {
    //List<TeamPost> findByUser_UserIdAndIsCompleteFalse(Long userId);
   // List<TeamPost> findByUser_UserIdAndIsCompleteTrue(Long userId);
    TeamPost findByPostId(Post post);
}
