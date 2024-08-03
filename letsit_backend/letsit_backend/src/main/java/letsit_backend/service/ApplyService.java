package letsit_backend.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import letsit_backend.dto.apply.ApplicantProfileDto;
import letsit_backend.dto.apply.ApplyRequestDto;
import letsit_backend.dto.apply.ApplyResponseDto;
import letsit_backend.model.Apply;
import letsit_backend.model.Member;
import letsit_backend.model.Post;
import letsit_backend.model.Profile;
import letsit_backend.repository.ApplyRepository;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PostRepository;
import letsit_backend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ApplyService {
    private final PostRepository postRepository;
    private final ApplyRepository applyRepository;
    private final ProfileRepository profileRepository;

    @PersistenceContext
    private EntityManager entityManager;


    public ApplyResponseDto create(Long postId, Member member, ApplyRequestDto request) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("구인글이 존재하지 않습니다."));
        List<Apply> applies = applyRepository.findByPostId(post);

        // 이미 지원했는지 찾아보고
        boolean alreadyApplied = applies.stream()
                .anyMatch(apply -> apply.getUserId().getUserId().equals(member.getUserId()));
        if (alreadyApplied) {
            throw new IllegalArgumentException("이미 지원한 게시글입니다.");
        }

        Apply apply = request.toEntity(post, member);
        Apply submittedApply = applyRepository.save(apply);

        return new ApplyResponseDto(submittedApply);
    }

    public ApplyResponseDto read(Long applyId, Member member) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));

        // 요청한 사람이 지원자 || 게시자 인지 확인
//        if (!member.getUserId().equals(apply.getUserId().getUserId()) || !member.getUserId().equals(apply.getPostId().getUserId().getUserId())) {
//            throw new AccessDeniedException("접근 권한이 없습니다.");
//        }
        return new ApplyResponseDto(apply);
    }

    public void delete(Long applyId, Member member) {
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));
        if (!member.getUserId().equals(apply.getUserId().getUserId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        applyRepository.delete(apply);
    }


    // 지원자 목록(프사, 닉넴) 리스트업
    @Transactional(readOnly = true)
    public List<ApplicantProfileDto> getApplicantProfiles(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("구인글이 존재하지 않습니다."));
        log.info(member.getUserId().toString());
        log.info(post.getUserId().getUserId().toString());
        if (!member.getUserId().equals(post.getUserId().getUserId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        List<Apply> applies = applyRepository.findByPostId(post);

        log.info("Applicants for post Id : {}", postId);
        return applies.stream()
                .filter(Apply::isNullYet)
                .map(apply-> {
                    Member applicant = apply.getUserId();
                    Profile profile = profileRepository.findByUserId(applicant);
                    return ApplicantProfileDto.fromEntity(profile, apply);
                })
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ApplicantProfileDto> getApprovedApplicantProfiles(Long postId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
        if (!member.getUserId().equals(post.getUserId().getUserId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        List<Apply> applies = applyRepository.findByPostId(post);
        return applies.stream()
                .filter(Apply::isApproved)
                .map(apply -> {
                    Member applicant = apply.getUserId();
                    Profile profile = profileRepository.findByUserId(applicant);
                    return ApplicantProfileDto.fromEntity(profile, apply);
                })
                .collect(Collectors.toList());
    }

    // 특정 지원자 승인 로직
    @Transactional
    public void approveApplicant(Long postId, Long applyId, Member member) {
        // log.info("Approving application. Post ID: {}, Apply ID: {}", postId, applyId);
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 구인글이 존재하지 않습니다."));
        if (!member.getUserId().equals(post.getUserId().getUserId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다."));

        post.approval(apply);
        applyRepository.save(apply);
        log.info("Application approved. Apply ID: {}", applyId);
    }
    // 특정 지원자 거절 로직
    public void rejectApplicant(Long postId, Long applyId, Member member) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("해당 구인글이 존재하지 않습니다."));
        if (!member.getUserId().equals(post.getUserId().getUserId())) {
            throw new AccessDeniedException("접근 권한이 없습니다.");
        }
        Apply apply = applyRepository.findById(applyId).orElseThrow(() -> new IllegalArgumentException("해당 지원서가 존재하지 않습니다."));

        post.reject(apply);
        applyRepository.save(apply);
    }
}
