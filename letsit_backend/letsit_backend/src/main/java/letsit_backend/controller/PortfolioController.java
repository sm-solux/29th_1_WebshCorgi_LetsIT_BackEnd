package letsit_backend.controller;

import letsit_backend.CurrentUser;
import letsit_backend.dto.Response;
import letsit_backend.dto.portfolio.DailyPortfolioListDto;
import letsit_backend.dto.portfolio.DailyPortfolioRequestDto;
import letsit_backend.dto.portfolio.DailyPortfolioResponseDto;
import letsit_backend.model.Member;
import letsit_backend.service.PortfolioService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Repeatable;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
    private final PortfolioService portfolioService;

    // TODO {userId} 삭제
    @PostMapping(value = "/{teamId}/write")
    public Response<DailyPortfolioResponseDto> post(@PathVariable("teamId") Long teamId, @CurrentUser Member member, @RequestBody DailyPortfolioRequestDto request) {
        DailyPortfolioResponseDto newPrt = portfolioService.create(teamId, member, request);
        return Response.success("성공", newPrt);
    }

    // TODO {userId} 삭제
    @GetMapping(value = "/{teamId}/list")
    public Response<List<DailyPortfolioListDto>> getPrtList(@PathVariable("teamId") Long teamId, @CurrentUser Member member) {
        List<DailyPortfolioListDto> prtList = portfolioService.getPrtList(teamId, member);
        return Response.success("포트폴리오 리스트", prtList);
    }

    // TODO {userId} 삭제
    @GetMapping(value = "/{teamId}/details/{prtId}")
    public Response<DailyPortfolioResponseDto> getPrt(@PathVariable("prtId") Long prtId, @CurrentUser Member member) {
        DailyPortfolioResponseDto prt = portfolioService.read(prtId, member);
        return Response.success("포트폴리오 상세조회", prt);
    }

    // 나의 포트폴리오 목록 리스트업
    @GetMapping(value = "/total/list")
    public Response<?> totalList(@CurrentUser Member member) {
        return Response.success("포트폴리오 전체목록 리스트업", portfolioService.totalList(member));
    }

    @GetMapping("/{teamId}/aiprt")
    public Response<String> getAiPrt(@PathVariable("teamId") Long teamId, @CurrentUser Member member) {
        String response = String.valueOf(portfolioService.aiPrt(teamId, member).getOutput());
        return Response.success("AI Portfolio Generated: ", response);
    }
}
