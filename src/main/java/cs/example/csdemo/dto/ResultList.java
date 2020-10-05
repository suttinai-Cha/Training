package cs.example.csdemo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ResultList<E> {

	@JsonProperty("resuls")
	private List<E> result;

	@JsonProperty("page_number")
	private Integer pageNumber;

	@JsonProperty("total_result")
	private Long total;
}
