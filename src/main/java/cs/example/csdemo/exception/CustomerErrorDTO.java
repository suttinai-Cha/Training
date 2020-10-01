package cs.example.csdemo.exception;
import java.util.Date;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;


@Data
public class CustomerErrorDTO {
	@JsonProperty("error")
	private String error;
	
	@JsonProperty("message")
	private String message;
	
	@JsonProperty("path")
	private String path;
	
	@JsonProperty("status")
	private int status;
	
	@JsonProperty("timestamp")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS",timezone = "Asia/Bangkok")
	private Date timeStamp;

	public CustomerErrorDTO(String error, String message, String path, int status, Date timeStamp) {
		super();
		this.error = error;
		this.message = message;
		this.path = path;
		this.status = status;
		this.timeStamp = timeStamp;
	}
	


}
