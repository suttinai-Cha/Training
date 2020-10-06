package cs.example.csdemo.client.dto;

import java.sql.Timestamp;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Data
public class SendEmailResponse {
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("message")
	
	private String message;
	
	@JsonProperty("send_date_time")
	private Timestamp send_date_time;
}
