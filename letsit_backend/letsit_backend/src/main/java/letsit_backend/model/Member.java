package letsit_backend.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;


@Builder
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column
    private Long kakaoId;

    @Column
    private String name;

    @Column
    private String ageRange;

    @Column
    private String gender;

    @Column
    private String profileImageUrl;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Setter
    private String kakaoAccessToken;


    @Builder
    public Member(String name, String ProfileImageUrl, Role role, Long kakaoId, String gender, String age_range, String kakaoAccessToken) {
        this.name = name;
        this.role = role;
        this.profileImageUrl = ProfileImageUrl;
        this.ageRange = age_range;
        this.kakaoId = kakaoId;
        this.gender = gender;
        this.kakaoAccessToken = kakaoAccessToken;
    }
}
