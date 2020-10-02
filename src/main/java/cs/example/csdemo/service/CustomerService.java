package cs.example.csdemo.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cs.example.csdemo.dto.Customer;
import cs.example.csdemo.entity.CustomerEntity;
import cs.example.csdemo.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Bean
	PropertyUtilsBean getPropertyUtilsBean() {
		return new PropertyUtilsBean();
	};

	@Autowired
	CustomerRepository customerRepository;

	public List<Customer> retrieveCustomer() {
		Iterable<CustomerEntity> customerEntity = customerRepository.findAll();
		List<Customer> returnList = new ArrayList<Customer>();
		customerEntity.forEach(orig -> {
			Customer dest = new Customer();
			try {
				getPropertyUtilsBean().copyProperties(dest, orig);
				returnList.add(dest);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				log.error(e.getMessage(), e);
			}
		});
		return new ArrayList<Customer>(returnList);
	}

	public Optional<Customer> createCustomer(Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (customerRepository.existsById(request.getPersonalId())) {
			return customerOptional;
		} else {
			CustomerEntity persit = new CustomerEntity();
			getPropertyUtilsBean().copyProperties(persit, request);
			persit = customerRepository.save(persit);
			customerOptional = Optional.of(request);
			return customerOptional;
		}
	}

	public Optional<Customer> updateCustomer(String personalId, Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (!customerRepository.existsById(request.getPersonalId())) {
			return customerOptional;
		} else {
			CustomerEntity persit = new CustomerEntity();
			getPropertyUtilsBean().copyProperties(persit, request);
			persit = customerRepository.save(persit);
			customerOptional = Optional.of(request);
			return customerOptional;
		}

	}

	public boolean deleteCustomer(String personalId) {
		if (!customerRepository.existsById(personalId)) {
			return false;
		} else {
			customerRepository.deleteById(personalId);
			return true;
		}
	}

	public Optional<Customer> retrieveCustomer(String personalId) {
		Optional<Customer> customerOptional = Optional.empty();
		if (!customerRepository.existsById(personalId)) {
			return customerOptional;
		} else {
			Optional<CustomerEntity> entityObj = customerRepository.findById(personalId);
			Customer returnCustomer = new Customer();
			try {
				getPropertyUtilsBean().copyProperties(returnCustomer, entityObj.get());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				e.printStackTrace();
			}
			customerOptional = Optional.of(returnCustomer);
			return customerOptional;
		}
	}

}
