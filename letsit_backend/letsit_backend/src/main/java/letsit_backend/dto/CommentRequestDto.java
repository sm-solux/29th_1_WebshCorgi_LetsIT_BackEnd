package letsit_backend.dto;

import letsit_backend.model.Comment;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequestDto {
    private Long postId;
    private Long userId;
    private String comContent;
    private Long commentId;

    public Comment toEntity(Post post, Member member) {
        return Comment.builder()
                .postId(post)
                .userId(member)
                .comContent(comContent)
                .build();
    }
}
