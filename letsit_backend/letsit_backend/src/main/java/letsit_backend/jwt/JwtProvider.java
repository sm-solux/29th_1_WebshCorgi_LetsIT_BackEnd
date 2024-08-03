package letsit_backend.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

//import io.jsonwebtoken.security.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import letsit_backend.model.Member;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    private static final String AUTHORITIES_KEY = "auth";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60L;

    public String createToken(Member member) {
        Date now = new Date();

        //사용자 권한을 가져와서 문자열로 변환
        String authorities = member.getRole().name();

        //claims 생성 및 사용자 정보 추가
        //Claims claims = Jwts.claims();
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", member.getUserId());
        claims.put(AUTHORITIES_KEY, authorities);

        String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(member.getKakaoId().toString())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRATION_TIME))
                .addClaims(claims)
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
                .compact();

        log.info("Generated JWT Token: {}", token); // JWT 토큰 로그 출력
        return token;
    }


    public String getSubject(String token) throws CustomException {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            log.debug("해독된 토큰: " + claims.getSubject());
            return claims.getSubject();
        } catch (JwtException e) {
            throw new CustomException("Invalid token", e);
        }
    }
    /* 유효성 확인 메소드 */
    public boolean validToken(String jwt) {
        try {
            Jwts.parser()
                    .setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                    .build()
                    .parseClaimsJws(jwt);
            return true;
        } catch (JwtException e) {
            log.error("유효하지 않은 JWT 토큰", e);
            return false;
        }
    }

}
