package cs.example.csdemo.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomRestClientTemplate {
	
	@Autowired
	protected RestTemplate restTemplate;
	
	public <T> ResponseEntity<T> postForEntity(URI url, @Nullable Object request, Class<T> responseType)
			throws RestClientException {
		log.info("request body: {}",request.toString());
		ResponseEntity<T> response = restTemplate.postForEntity(url, request, responseType);
		log.info("response body: {}",response.toString());
		return response;
	}
	
}
