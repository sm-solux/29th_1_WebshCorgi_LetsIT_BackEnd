package letsit_backend.dto;

import letsit_backend.model.Member;
import lombok.Data;

@Data
public class LoginResponseDto {
    public boolean loginSuccess;
    public Member member;
}
