package letsit_backend.service;


import letsit_backend.dto.portfolio.AiPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.model.ProjectPortfolio;
import letsit_backend.model.TeamMember;
import letsit_backend.model.TeamPost;
import letsit_backend.repository.MemberRepository;
import letsit_backend.repository.PortfolioRepository;
import letsit_backend.repository.TeamMemberRepository;
import letsit_backend.repository.TeamPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PortfolioService {
    private final TeamPostRepository teamPostRepository;
    private final MemberRepository memberRepository;
    private final PortfolioRepository portfolioRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final OpenAiChatModel openAiChatModel;

    public DailyPortfolioResponseDto create(Long teamId, Member member, DailyPortfolioRequestDto request) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        // 보안을 위해 {userId}를 받아야 할까?
        ProjectPortfolio prt = request.toEntity(team, member);
        portfolioRepository.save(prt);

        return new DailyPortfolioResponseDto(prt);
    }


    public List<DailyPortfolioListDto> getPrtList(Long teamId, Member member) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        List<ProjectPortfolio> prt = portfolioRepository.findAllByTeamIdAndUserId(team, member);

        return prt.stream()
                .sorted((p1, p2) -> p2.getPrtId().compareTo(p1.getPrtId()))
                .map(p -> new DailyPortfolioListDto(
                        p.getTeamId().getTeamId(),
                        p.getPrtId(),
                        p.getPrtTitle(),
                        p.getPrtCreateDate()
                ))
                .collect(Collectors.toList());
    }

    public DailyPortfolioResponseDto read(Long prtId, Member member) {
        ProjectPortfolio prt = portfolioRepository.findById(prtId).orElseThrow(() -> new IllegalArgumentException("신청서가 존재하지 않습니다."));;
        if (!prt.getUserId().getUserId().equals(member.getUserId())) {
            throw new AccessDeniedException("댓글을 수정할 권한이 없습니다.");
        }
        return new DailyPortfolioResponseDto(prt);
    }

    public List<Map<Long, String>> totalList(Member member) {
        List<TeamMember> teamMemberList = teamMemberRepository.findAllByUserId(member);

        // Map each team member to a map containing the team ID and project title
        List<Map<Long, String>> teamPostList = teamMemberList.stream()
                .map(teamMember -> {
                    Map<Long, String> map = new HashMap<>();
                    map.put(teamMember.getTeamId().getTeamId(), teamMember.getTeamId().getPrjTitle());
                    return map;
                })
                .collect(Collectors.toList());

        return teamPostList;
    }
    public Generation aiPrt(Long teamId, Member target) {
        TeamPost team = teamPostRepository.findById(teamId).orElseThrow(() -> new IllegalArgumentException("유효하지 않은 프로젝트입니다."));
        Member member = memberRepository.findById(target.getUserId()).orElseThrow(() -> new IllegalArgumentException(""));
        List<ProjectPortfolio> prtList = portfolioRepository.findAllByTeamIdAndUserId(team, member);
        List<AiPortfolioRequestDto> request = prtList.stream().map(prt -> new AiPortfolioRequestDto(prt.getWorkDescription(), prt.getIssues(), prt.getSolutions(), prt.getFeedback())).collect(Collectors.toList());

        // 프롬포트 = SystemMessage + UserMessage
        List<Message> prompts = new ArrayList<>();
        // Message[] prompts = new Message[request.size() + 1];
        String architecture = "넌 개발자들의 IT 기업 취업을 위해 자기소개서 작성을 돕는 컨설턴트야. 사용자 입력으로 [수행한 일, 수행 과정 중 겪은 어려움, 해결 과정, 얻은 깨달음]이 여러 묶음 들어올 거야. 우린 이걸 TIL 이라 불러. 이 TIL들을 묶어 하나의 글로 재구성한 자기소개서 포트폴리오를 작성해줘. 문제를 해결하면서 얻은 깨달음, 성장한 부분에 중점을 두면 좋아.";
        // String testPrompt = "'자소서 컨설턴트'란 말을 출력한 뒤, 사용자 입력에서 ': ' 뒤의 말들만 뽑아서 열거해 개행문자 단위로 끊어서 보여줘..";
        // 역할 부여, 행동 지정
        Message role = new SystemMessage(architecture);
        // Message role = new SystemMessage(testPrompt);
        //log.info("SystemMessage : {}", role);

        // 사용자 input: daily-portfolios
        for (AiPortfolioRequestDto daily : request) {
            String command = "수행한 일: {WorkDescriptions}" + System.lineSeparator() + "수행 과정 중 겪은 어려움: {Issues}" + System.lineSeparator() + "해결 과정: {Solutions}" + System.lineSeparator() + "얻은 깨달음: {Feedback}";
            PromptTemplate template = new PromptTemplate(command);

            Message userMessage = template.createMessage(Map.of("WorkDescriptions", daily.getWorkDescription(),
                    "Issues", daily.getIssues(),
                    "Solutions", daily.getSolutions(),
                    "Feedback", daily.getFeedback()));
            // log.info("message: {}", userMessage);
            prompts.add(userMessage);
            /*
            template.add("Work Descriptions", daily.getWorkDescription());
            template.add("Issues", daily.getIssues());
            template.add("Solutions", daily.getSolutions());
            template.add("Feedback", daily.getFeedback());
            String message = template.render(); // template 의 {}에 메세지 할당하고 String 타입으로 리턴
            Message userMessage = new UserMessage(message);
            log.info("message: {}", userMessage);
             */
        }
        prompts.add(0, role);
        Prompt totalPrompts = new Prompt(prompts);
        return openAiChatModel.call(totalPrompts).getResult();
    }
}
