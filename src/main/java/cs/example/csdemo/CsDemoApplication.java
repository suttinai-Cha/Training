package cs.example.csdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CsDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CsDemoApplication.class, args);
	}
	
	// เมื่อใส่ @Bean บน method คือการประกาศ method restTemplate ให้ spring boot จัดการ
 	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
	   return builder.build();
	}
}
