package letsit_backend.repository;

import jakarta.persistence.criteria.*;
import letsit_backend.model.Post;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class PostSpecification {
    // TODO 대소문자 구분없이 검색하도록 설정
    public static Specification<Post> containsKeywordsInTitleOrContent(List<String> keywords) {
        return (root, query, builder) -> {
            if (keywords == null ||keywords.isEmpty()) {
                return builder.conjunction();
            }

            Predicate[] predicates = new Predicate[keywords.size()];
            for (int i = 0; i < keywords.size(); i++) {
                String keyword = "%" + keywords.get(i) + "%";
                Predicate titlePredicate = builder.like(root.get("title"), keyword);
                Predicate contentPredicate = builder.like(root.get("content"), keyword);
                predicates[i] = builder.or(titlePredicate, contentPredicate);
            }
            return builder.and(predicates);
        };
    }

    // TODO a&b우선, a|b를 후순위로 리스트업하는 검색알고리즘
    // TODO 각검색에 대해 점수를 부여하여 출력


}
