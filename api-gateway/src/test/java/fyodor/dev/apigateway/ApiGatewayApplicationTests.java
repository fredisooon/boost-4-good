package fyodor.dev.apigateway;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
class ApiGatewayApplicationTests {

	@Test
	void contextLoads() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] secret = new byte[128];
		secureRandom.nextBytes(secret);
		String s = Base64.getEncoder().encodeToString(secret);

	}

}
