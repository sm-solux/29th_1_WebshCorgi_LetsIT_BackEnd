package letsit_backend;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ComponentScan(basePackages = "letsit_backend") // TestProperties를 스캔할 수 있도록 패키지 명시

public class LetsitBackendApplicationTests {

	@Autowired
	private TestProperties testProperties; // TestProperties를 주입받기 위한 필드

	@Test
	public void testKakaoProperties() {
		testProperties.run(); // TestProperties의 run 메서드 호출
	}

	@Component
	public static class TestProperties implements CommandLineRunner {

		@Value("${kakao.client.id}")
		private String clientId;

		@Value("${kakao.redirect.uri}")
		private String redirectUri;

		@Override
		public void run(String... args) {
			try {
				System.out.println("Kakao Client ID: " + clientId);
				System.out.println("Kakao Redirect URI: " + redirectUri);
			} catch (Exception e) {
				// 예외 처리 로직 추가
				e.printStackTrace();
			}
		}
	}

}