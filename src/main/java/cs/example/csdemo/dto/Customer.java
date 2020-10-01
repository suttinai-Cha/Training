package cs.example.csdemo.dto;





import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Customer {
	
	@JsonProperty("first_name")
	@NotEmpty(message = "must not be empty") @NotNull(message = "must not be null")
	@Length(max = 50 )
	private String name;
	
	@JsonProperty("last_name")
	@NotEmpty(message = "must not be empty") @NotNull(message = "must not be null")
	@Length(max = 50 )
	private String lastname;
	
	@JsonProperty("personal_id")
	@NotEmpty(message = "must not be empty") @NotNull(message = "must not be null")
	@Length(max = 13,min = 13 )
	@Pattern(regexp="(^$|[0-9]{13})", message = "must not contain numbers")
	private String personalId;
	
	@JsonProperty("birth_date")
	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd")
	private Date birthDate;
	

}
