package cs.example.csdemo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

import cs.example.csdemo.utils.Constraint.LogType;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "TBL_ACTIVITYLOG")
@Getter
public class LogEntity {

	public LogEntity(String request_id, String ip_address, String message, LogType log_type, Date create_date) {
		super();
		this.request_id = request_id;
		this.message = message;
		this.log_type = log_type.value();
		this.create_date = create_date;
		this.ip_address = ip_address;
	}

	@Column(name = "LOG_ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int log_id;

	@Column(name = "REQUEST_ID")
	@Length(max = 50)
	private String request_id;

	@Column(name = "IP_ADDRESS")
	@Length(max = 50)
	private String ip_address;

	@Column(name = "MESSAGE")
	@Length(max = 250)
	private String message;

	@Column(name = "LOG_TYPE")
	@Length(max = 15)
	private String log_type;

	@Column(name = "CREATE_DATE")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", timezone = "Asia/Bangkok")
	@Temporal(TemporalType.TIMESTAMP)
	private Date create_date;
}
