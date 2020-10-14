package cs.example.csdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table(name = "TBL_CUSTOMER")
@NamedQueries({ 
	@NamedQuery(name = "customer.get-all-by-name", 
			query = "select cus from CustomerEntity cus where cus.name like :NAME ") })
public class CustomerEntity {
	@Column(name = "FIRSTNAME")
	@Length(max = 50)
	private String name;

	@Column(name = "LASTNAME")
	@Length(max = 50)
	private String lastname;

	@Id
	@Column(name = "PERSONAL_ID")
	@Length(max = 13, min = 13)
	private String personalId;

	@Column(name = "BIRTH_DATE")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Bangkok")
	@Temporal(TemporalType.DATE)
	private Date birthDate;
	
	
}
