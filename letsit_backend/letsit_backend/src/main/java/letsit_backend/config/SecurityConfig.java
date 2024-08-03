package letsit_backend.config;

//import org.apache.catalina.filters.CorsFilter;
import letsit_backend.jwt.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
//import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Slf4j
@Configuration
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    //private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class);

    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests

                                .requestMatchers("/", "/home","/apply/**", "/login/**", "/vite.svg", "/posts/**", "/favicon.ico", "/error", "/login/oauth2/callback/kakao", "/profile/**", "index.html").permitAll() // 정적 파일 및 특정 경로 허용
                                .anyRequest().authenticated()
                )

                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")
                                //.defaultSuccessUrl("/", true)
                                .permitAll()
                )

                /*
                .logout(logout ->
                        logout
                                .logoutSuccessUrl("/")
                                .permitAll()
                )

                 */
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 정책을 STATELESS로 설정
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class); // JwtFilter를 UsernamePasswordAuthenticationFilter 앞에 추가
                //.formLogin(formLogin -> formLogin.disable()); // 기본 로그인 폼 비활성화
        http.logout(logout -> logout.disable());
        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:5173", "http://localhost:8080", "http://localhost:80", "http://172.20.8.238:80", "http://172.20.8.238:8080"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        source.registerCorsConfiguration("/**", configuration);

        log.info("Configured CORS with allowed origins: {}", configuration.getAllowedOrigins());

        return source;
    }
}