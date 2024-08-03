package letsit_backend.service;

import letsit_backend.model.Post;
import letsit_backend.repository.PostRepository;
import letsit_backend.repository.PostSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SearchService {
    private final PostRepository postRepository;

    // 한개의 키워드서비스
    public List<Post> searchPostsByKeyword(String keyword) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return postRepository.findByTitleContainingOrContentContaining(keyword,keyword, sort);
    }

    // 여러개의 키워드서비스
    // TODO 여전히 여러키워드가 각자 다른글을 가리키면 검색안됨. -> a제목, b제목 같이검색시 null출력
    public List<Post> searchPostsByKewords(List<String> keywords) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        //return postRepository.findAll(PostSpecification.containsKeywordsInTitleOrContent(keywords));
        return postRepository.findAll(PostSpecification.containsKeywordsInTitleOrContent(keywords));
    }

    // TODO 각 키워드를 모두포함하는것, 한개만 포함하는것등 점수를 매겨 우선순위에따라 리스트해주는 알고리즘 필요

    // 페이징
    public Page<Post> paging(String keywords, Pageable pageable) {
        Page<Post> posts = postRepository.findByTitleContaining(keywords, pageable);
        return posts;
    }



}
