package letsit_backend.repository;

import letsit_backend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import letsit_backend.model.Post;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Post postId);
}
