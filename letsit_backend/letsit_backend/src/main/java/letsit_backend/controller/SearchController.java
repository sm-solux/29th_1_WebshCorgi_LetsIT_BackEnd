package letsit_backend.controller;

import letsit_backend.model.Post;
import letsit_backend.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchController {
    private final SearchService searchService;


    // 한개의 키워드를 받아온 검색기능
    @GetMapping("/search")
    public List<Post> searchPostsKeyword(@RequestParam String keyword) {
        return searchService.searchPostsByKeyword(keyword);
    }

    // 여러개의 키워드를 받아온 검색기능
    @GetMapping("/searchs")
    public List<Post> searchPostsKeywords(@RequestParam List<String> keywords) {
        // TODO 키워드검색시 두글자이상 적도록 하기
        // TODO SearchResponseDto로 필요정보만 절달하도록 설정하기
        return searchService.searchPostsByKewords(keywords);
    }



    // TODO 여러키워드검색과 연결하기
    @GetMapping("/paging")
    public Page<Post> paging(@RequestParam String keywords, @PageableDefault(sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return searchService.paging(keywords, pageable);
    }


}
