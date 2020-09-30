package cs.example.csdemo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import cs.example.csdemo.dto.Customer;

@Service
public class CustomerService {

	protected Map<String, Customer> customers = new HashMap<String, Customer>();;

	public List<Customer> retrieveCustomer() {
		return new ArrayList<Customer>(customers.values());
	}

	public Optional<Customer> createCustomer(Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (customers.containsKey(request.getPersonalId())) {
			return customerOptional;
		} else {
			customers.put(request.getPersonalId(), request);
			customerOptional = Optional.of(request);
			return customerOptional;
		}
	}

	public Optional<Customer> updateCustomer(String personalId, Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (!customers.containsKey(personalId)) {
			return customerOptional;
		} else {
			customers.remove(personalId);
			customers.put(request.getPersonalId(), request);
			customerOptional = Optional.of(request);
			return customerOptional;
		}

	}

	public boolean deleteCustomer(String personalId) {
		if (!customers.containsKey(personalId)) {
			return false;
		} else {
			customers.remove(personalId);
			return true;
		}
	}

	public Optional<Customer> retrieveCustomer(String personalId) {
		Optional<Customer> customerOptional = Optional.empty();
		if (!customers.containsKey(personalId)) {
			return customerOptional;
		} else {
			customerOptional = Optional.of(customers.get(personalId));
			return customerOptional;
		}
	}

}
