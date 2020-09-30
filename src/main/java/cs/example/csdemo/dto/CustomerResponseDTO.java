package cs.example.csdemo.dto;

import java.util.Date;
import java.util.UUID;

import lombok.Data;

@Data
public class CustomerResponseDTO {
	private UUID txnId;
	private String status;
	private String message;
	private Customer customer;
	private Date timestamp;
}
