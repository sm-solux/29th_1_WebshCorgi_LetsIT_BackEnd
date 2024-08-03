package letsit_backend.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Properties;
@Data
@NoArgsConstructor
public class KakaoProfile {
    public Long id;
    public String connected_at;
    public Properties properties;
    public KakaoAccount kakao_account; // 수정: JSON 응답에 따라 kakao_Account -> kakao_account

    @Data
    public static class Properties { // 수정: class에 static 추가
        public String profile_image;
        public String thumbnail_image; // 추가: JSON 응답에 있는 필드 추가
    }

    @Data
    public static class KakaoAccount { // 수정: class에 static 추가
        public Boolean profile_image_needs_agreement;
        public Profile profile;
        public Boolean name_needs_agreement;
        public String name;
        public Boolean has_age_range;
        public Boolean age_range_needs_agreement;
        public String age_range;
        public Boolean has_gender;
        public Boolean gender_needs_agreement;
        public String gender;

        // 제거: 사용되지 않는 필드들
        // public Boolean profile_nickname_needs_agreement;
        // public Boolean has_email;
        // public Boolean email_needs_agreement;
        // public Boolean is_email_valid;
        // public Boolean is_email_verified;
        // public String email;

        @Data
        public static class Profile {
            public String thumbnail_image_url; // 추가: JSON 응답에 있는 필드 추가
            public String profile_image_url;
            public Boolean is_default_image;
        }
    }


}
