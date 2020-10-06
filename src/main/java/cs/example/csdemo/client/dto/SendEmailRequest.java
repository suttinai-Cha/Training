package cs.example.csdemo.client.dto;



import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SendEmailRequest {
	
	@JsonProperty("personal_id")
	private String personalId;
	
}
