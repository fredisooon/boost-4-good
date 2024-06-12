package fyodor.dev.charitymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CharityMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharityMicroserviceApplication.class, args);
	}

}
