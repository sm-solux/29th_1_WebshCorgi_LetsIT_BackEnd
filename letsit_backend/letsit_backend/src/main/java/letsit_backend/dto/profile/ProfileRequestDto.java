package letsit_backend.dto.profile;

import letsit_backend.model.Profile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileRequestDto {
    private Long userId;
    private Long profileId;
    private Profile.Manner_tier mannerTier = Profile.Manner_tier.B;
    private double mannerScore = 75.0;
    private String nickname;
    private String name;
    private String age;
    private Map<String, String> sns;
    private String profileImageUrl;
    private String bio;
    private String selfIntro;
    private Map<String, Integer> skills;
}

