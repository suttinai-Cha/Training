package cs.example.csdemo.controler;

import org.springframework.web.bind.annotation.RestController;

import cs.example.csdemo.dto.Customer;
import cs.example.csdemo.dto.CustomerResponseDTO;
import cs.example.csdemo.exception.CustomException;
import cs.example.csdemo.service.CustomerService;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController()
@RequestMapping("/customers")
@Validated
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Customer> getCustomers() {
		return customerService.retrieveCustomer();
	}
	
	@GetMapping(params = "personalId")
	public ResponseEntity<?> getCustomer(@RequestParam(value = "personalId")  @Length(max = 13,min = 13, message = "personalId: length must be between {min} and {max}") String personalId) {
		Optional<Customer> customer = customerService.retrieveCustomer(personalId);
		if (!customer.isPresent()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found customer for ID:" + personalId);
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(customer);
	}
	
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addustomer(@Validated @RequestBody Customer request) throws Exception {
		Optional<Customer> customer = customerService.createCustomer(request);
		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		if (!customer.isPresent()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body("customer id:" + request.getPersonalId() + " is already created");
		}
		responseDTO.setTxnId(UUID.randomUUID());
		responseDTO.setTimestamp(new Date());
		responseDTO.setStatus("SUCCESS");
		responseDTO.setCustomer(customer.get());
		responseDTO.setMessage("Create customer id:" + request.getPersonalId()+" success");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@PutMapping(path = "/{personalId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateCustomer(@PathVariable String personalId, @Validated @RequestBody Customer request)
			throws Exception {
		Optional<Customer> customer = customerService.updateCustomer(personalId, request);
		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		if (!customer.isPresent()) {
			throw new CustomException("Not found","Not found customer for ID:" + personalId);
		}
		responseDTO.setTxnId(UUID.randomUUID());
		responseDTO.setTimestamp(new Date());
		responseDTO.setStatus("SUCCESS");
		responseDTO.setCustomer(customer.get());
		responseDTO.setMessage("Update customer id:" + personalId+" success");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

	@DeleteMapping("/{personalId}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String personalId) {
		CustomerResponseDTO responseDTO = new CustomerResponseDTO();
		if (!customerService.deleteCustomer(personalId)) {
			throw new CustomException("Not found","Not found customer for ID:" + personalId);
		}
		responseDTO.setTxnId(UUID.randomUUID());
		responseDTO.setStatus("SUCCESS");
		responseDTO.setTimestamp(new Date());
		responseDTO.setMessage("Delete customer id:" + personalId+" success");
		return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
	}

}