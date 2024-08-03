package letsit_backend.controller;

import letsit_backend.CurrentUser;
import letsit_backend.dto.Response;
import letsit_backend.dto.comment.CommentRequestDto;
import letsit_backend.dto.comment.CommentResponseDto;
import letsit_backend.model.Member;
import letsit_backend.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    // TODO {userId} 빼기
    @PostMapping("/{postId}/upload")
    public Response<CommentResponseDto> postNewComment(@PathVariable("postId") Long postId, @CurrentUser Member member, @RequestBody CommentRequestDto request) {
        CommentResponseDto savedComment = commentService.upload(postId, member, request);

        return Response.success("완료", savedComment);
    }

    // TODO {userId} 빼기
    @PatchMapping("/{postId}/update/{commentId}")
    public Response<CommentResponseDto> updateComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @CurrentUser Member member, @RequestBody CommentRequestDto request) {
        CommentResponseDto updatedComment = commentService.updateComment(postId, commentId, member, request);
        return Response.success("댓글 수정 완료", updatedComment);
    }

    // TODO {postId} 빼기
    @DeleteMapping("/{postId}/delete/{commentId}")
    public Response<String> deleteComment(@PathVariable("postId") Long postId, @PathVariable("commentId") Long commentId, @CurrentUser Member member) {
        commentService.delete(postId, commentId, member);
        return Response.success("댓글을 삭제하였습니다.", null);
    }
}
