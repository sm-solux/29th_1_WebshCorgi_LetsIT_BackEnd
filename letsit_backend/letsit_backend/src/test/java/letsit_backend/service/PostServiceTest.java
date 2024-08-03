//package letsit_backend.service;
//
//import letsit_backend.dto.PostRequestDto;
//import letsit_backend.dto.PostResponseDto;
//import letsit_backend.model.Member;
//import letsit_backend.model.Post;
//import letsit_backend.repository.MemberRepository;
//import letsit_backend.repository.PostRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import java.sql.Timestamp;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class PostServiceTest {
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private PostRequestDto postRequestDto;
//    private Long userId;
//
//    @BeforeEach
//    public void setUp() {
//        // Create a test member
//        Member member = Member.builder()
//                .loginId("testuser")
//                .password("password")
//                .email("testuser@example.com")
//                .name("Test User")
//                .birth("1990-01-01")
//                .gender("M")
//                .build();
//        Member savedMember = memberRepository.save(member);
//        userId = savedMember.getUserId();
//
//        // Setup the post request DTO
//        postRequestDto = new PostRequestDto();
//        postRequestDto.setTitle("Test Title");
//        postRequestDto.setContent("Test Content");
//        postRequestDto.setPeopleNum(5);
//
//        PostRequestDto.RecruitPeriod recruitPeriod = new PostRequestDto.RecruitPeriod();
//        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
//        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
//        postRequestDto.setRecruitPeriod(recruitPeriod);
//
//        postRequestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");
//
//        PostRequestDto.ProjectInfo projectInfo = new PostRequestDto.ProjectInfo();
//        projectInfo.setProjectPeriod(Post.projectPeriod.threeMonths);
//        projectInfo.setAgeGroup(Post.AgeGroup.s20c);
//        postRequestDto.setProjectInfo(projectInfo);
//
//        postRequestDto.setStack(List.of("Java", "React"));
//        postRequestDto.setDifficulty(Post.Difficulty.basic);
//        postRequestDto.setOnOff(true);
//        postRequestDto.setRegionId(1L);
//        postRequestDto.setCategoryId(1L);
//    }
//
//    @Test
//    public void testCreatePost() {
//        PostResponseDto responseDto = postService.createPost(postRequestDto);
//
//        assertThat(responseDto).isNotNull();
//        assertThat(responseDto.getTitle()).isEqualTo("Test Title");
//        assertThat(responseDto.getContent()).isEqualTo("Test Content");
//        assertThat(responseDto.getPeopleNum()).isEqualTo(5);
//    }
//
//    @Test
//    public void testDeletePost() {
//        PostResponseDto responseDto = postService.createPost(postRequestDto);
//        boolean isDeleted = postService.deletePost(userId, responseDto.getPostId());
//
//        assertThat(isDeleted).isTrue();
//    }
//
//    @Test
//    public void testGetPostById() {
//        PostResponseDto createdPost = postService.createPost(postRequestDto);
//        PostResponseDto fetchedPost = postService.getPostById(createdPost.getPostId());
//
//        assertThat(fetchedPost).isNotNull();
//        assertThat(fetchedPost.getTitle()).isEqualTo("Test Title");
//        assertThat(fetchedPost.getContent()).isEqualTo("Test Content");
//        assertThat(fetchedPost.getPeopleNum()).isEqualTo(5);
//    }
//
//    @Test
//    public void testClosePost() {
//        PostResponseDto createdPost = postService.createPost(postRequestDto);
//        boolean isClosed = postService.closePost(createdPost.getPostId());
//
//        assertThat(isClosed).isTrue();
//        PostResponseDto fetchedPost = postService.getPostById(createdPost.getPostId());
//        assertThat(fetchedPost.getDeadline()).isTrue();
//    }
//}
///////////////////////
//package letsit_backend.service;
//
//import letsit_backend.dto.PostRequestDto;
//import letsit_backend.dto.PostResponseDto;
//import letsit_backend.dto.PostRequestDto.ProjectInfo;
//import letsit_backend.dto.PostRequestDto.RecruitPeriod;
//import letsit_backend.model.Post;
//import letsit_backend.repository.PostRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.sql.Timestamp;
//import java.util.Arrays;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//
//@SpringBootTest
//@Transactional
//class PostServiceTest {
//
//    @Autowired
//    private PostService postService;
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Test
//    @Rollback(value = false)
//    void testCreatePost() {
//        // given
//        PostRequestDto requestDto = new PostRequestDto();
//        requestDto.setTitle("Test Title");
//        requestDto.setContent("Test Content");
//        requestDto.setPeopleNum(Post.PeopleNum.TWO);
//
//        RecruitPeriod recruitPeriod = new RecruitPeriod();
//        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
//        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
//        requestDto.setRecruitPeriod(recruitPeriod);
//
//        ProjectInfo projectInfo = new ProjectInfo();
//        projectInfo.setProjectPeriod(Post.projectPeriod.THREE);
//        projectInfo.setAgeGroup(Post.AgeGroup.s20b);
//        requestDto.setProjectInfo(projectInfo);
//
//        requestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");
//        requestDto.setStack(Arrays.asList("Java", "React"));
//        requestDto.setDifficulty(Post.Difficulty.BASIC);
//        requestDto.setOnOff(true);
//        requestDto.setCategoryId(1L);
//        requestDto.setRegionId(1L);
//
//        // when
//        PostResponseDto responseDto = postService.createPost(requestDto);
//
//        // then
//        assertNotNull(responseDto);
//        assertNotNull(responseDto.getPostId());
//        assertThat(responseDto.getTitle()).isEqualTo("Test Title");
//    }
//
//    @Test
//    @Rollback(value = false)
//    void testDeletePost() {
//        // given
//        PostRequestDto requestDto = new PostRequestDto();
//        requestDto.setTitle("Test Title");
//        requestDto.setContent("Test Content");
//        requestDto.setPeopleNum(Post.PeopleNum.TWO);
//
//        RecruitPeriod recruitPeriod = new RecruitPeriod();
//        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
//        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
//        requestDto.setRecruitPeriod(recruitPeriod);
//
//        ProjectInfo projectInfo = new ProjectInfo();
//        projectInfo.setProjectPeriod(Post.projectPeriod.THREE);
//        projectInfo.setAgeGroup(Post.AgeGroup.s20b);
//        requestDto.setProjectInfo(projectInfo);
//
//        requestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");
//        requestDto.setStack(Arrays.asList("Java", "React"));
//        requestDto.setDifficulty(Post.Difficulty.BASIC);
//        requestDto.setOnOff(true);
//        requestDto.setCategoryId(1L);
//        requestDto.setRegionId(1L);
//
//        PostResponseDto createdPost = postService.createPost(requestDto);
//
//        // when
//        boolean isDeleted = postService.deletePost(createdPost.getPostId());
//
//        // then
//        assertThat(isDeleted).isTrue();
//        assertThat(postRepository.findById(createdPost.getPostId())).isEmpty();
//    }
//
//    @Test
//    @Rollback(value = false)
//    void testUpdatePost() {
//        // given
//        PostRequestDto requestDto = new PostRequestDto();
//        requestDto.setTitle("Test Title");
//        requestDto.setContent("Test Content");
//        requestDto.setPeopleNum(Post.PeopleNum.TWO);
//
//        RecruitPeriod recruitPeriod = new RecruitPeriod();
//        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
//        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
//        requestDto.setRecruitPeriod(recruitPeriod);
//
//        ProjectInfo projectInfo = new ProjectInfo();
//        projectInfo.setProjectPeriod(Post.projectPeriod.THREE);
//        projectInfo.setAgeGroup(Post.AgeGroup.s20b);
//        requestDto.setProjectInfo(projectInfo);
//
//        requestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");
//        requestDto.setStack(Arrays.asList("Java", "React"));
//        requestDto.setDifficulty(Post.Difficulty.BASIC);
//        requestDto.setOnOff(true);
//        requestDto.setCategoryId(1L);
//        requestDto.setRegionId(1L);
//
//        PostResponseDto createdPost = postService.createPost(requestDto);
//
//        // 업데이트할 내용 설정
//        requestDto.setTitle("Updated Title");
//        requestDto.setContent("Updated Content");
//
//        // when
//        PostResponseDto updatedPost = postService.updatePost(createdPost.getPostId(), requestDto);
//
//        // then
//        assertNotNull(updatedPost);
//        assertThat(updatedPost.getTitle()).isEqualTo("Updated Title");
//        assertThat(updatedPost.getContent()).isEqualTo("Updated Content");
//    }
//
//    @Test
//    @Rollback(value = false)
//    void testClosePost() {
//        // given
//        PostRequestDto requestDto = new PostRequestDto();
//        requestDto.setTitle("Test Title");
//        requestDto.setContent("Test Content");
//        requestDto.setPeopleNum(Post.PeopleNum.TWO);
//
//        RecruitPeriod recruitPeriod = new RecruitPeriod();
//        recruitPeriod.setStartDate(Timestamp.valueOf("2024-07-01 00:00:00"));
//        recruitPeriod.setEndDate(Timestamp.valueOf("2024-07-31 23:59:59"));
//        requestDto.setRecruitPeriod(recruitPeriod);
//
//        ProjectInfo projectInfo = new ProjectInfo();
//        projectInfo.setProjectPeriod(Post.projectPeriod.THREE);
//        projectInfo.setAgeGroup(Post.AgeGroup.s20b);
//        requestDto.setProjectInfo(projectInfo);
//
//        requestDto.setPreference("관련 경력 3년 이상, Git 사용 경험");
//        requestDto.setStack(Arrays.asList("Java", "React"));
//        requestDto.setDifficulty(Post.Difficulty.BASIC);
//        requestDto.setOnOff(true);
//        requestDto.setCategoryId(1L);
//        requestDto.setRegionId(1L);
//
//        PostResponseDto createdPost = postService.createPost(requestDto);
//
//        // when
//        boolean isClosed = postService.closePost(createdPost.getPostId());
//
//        // then
//        assertThat(isClosed).isTrue();
//        PostResponseDto fetchedPost = postService.getPostById(createdPost.getPostId());
//        assertThat(fetchedPost.getDeadline()).isTrue();
//    }
//}

