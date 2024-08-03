package letsit_backend.dto.profile;

import letsit_backend.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponseDto {
    private Long profileId;
    private Long userId;
    private Profile.Manner_tier mannerTier;
    private double mannerScore;
    private String name;
    private String nickname;
    private String age;
    private Map<String, String> sns;
    private String profileImageUrl;
    private String bio;
    private String selfIntro;
    private Map<String, Integer> skills;
}
