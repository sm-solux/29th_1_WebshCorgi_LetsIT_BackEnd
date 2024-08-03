package letsit_backend.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import letsit_backend.model.Member;
import letsit_backend.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        String token = null;
        String kakaoId = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            logger.info(token);
            try {
                kakaoId = jwtProvider.getSubject(token);
            } catch (IllegalArgumentException e) {
                logger.error("An error occurred while fetching the username from the token", e);
            } catch (ExpiredJwtException e) {
                logger.warn("The token is expired and not valid anymore", e);
            } catch (SignatureException e) {
                logger.error("Authentication Failed. Username or Password not valid.");
            }
        } else {
            logger.warn("Couldn't find bearer string, header will be ignored");
        }

        if (kakaoId != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            Optional<Member> memberOptional = memberRepository.findByKakaoId(Long.parseLong(kakaoId));
            if (memberOptional.isPresent() && jwtProvider.validToken(token)) {
                Member member = memberOptional.get();
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        member, null, Collections.emptyList());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("Authenticated user " + member.getName() + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } else {
                logger.warn("No member found with kakaoId: " + kakaoId);
            }
        } else {
            logger.warn("Invalid JWT token for kakaoId: " + kakaoId);
        }

        chain.doFilter(request, response);
    }
}
