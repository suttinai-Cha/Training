package cs.example.csdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import cs.example.csdemo.dto.Customer;
import lombok.Data;

@Data
@Entity
@Table(name = "Customer")
public class CustomerEntity {
	@Column(name = "firstname")
	@Length(max = 50)
	private String name;
	
	
	@Column(name = "lastname")
	@Length(max = 50)
	private String lastname;
	
	@Id
	@Column(name = "personal_id")
	@Length(max = 13,min = 13)
	private String personalId;
	
	@Column(name = "birth_date")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bangkok")
    @Temporal(TemporalType.DATE)
	private Date birthDate;
}
