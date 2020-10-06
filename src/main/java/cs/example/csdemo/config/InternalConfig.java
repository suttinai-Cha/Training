package cs.example.csdemo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@ConfigurationProperties(prefix = "external-service")
@Configuration
@Setter
@Getter
public class InternalConfig {
	
	String sendEmailUrl;
}
