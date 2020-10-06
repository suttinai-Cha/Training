package cs.example.csdemo.service;

import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.beanutils.PropertyUtilsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import cs.example.csdemo.client.CustomRestClientTemplate;
import cs.example.csdemo.client.dto.SendEmailRequest;
import cs.example.csdemo.client.dto.SendEmailResponse;
import cs.example.csdemo.config.InternalConfig;
import cs.example.csdemo.dto.Customer;
import cs.example.csdemo.dto.ResultList;
import cs.example.csdemo.entity.CustomerEntity;
import cs.example.csdemo.repository.CustomerRepository;
import cs.example.csdemo.repository.IcustomerRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CustomerService {

	@Bean
	PropertyUtilsBean getPropertyUtilsBean() {
		return new PropertyUtilsBean();
	};

	@Autowired
	IcustomerRepository icustomerRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired 
	InternalConfig config;
	public List<Customer> retrieveCustomer() {
		Iterable<CustomerEntity> customerResult = icustomerRepository.findAll();
		List<Customer> returnList = new ArrayList<Customer>();
		customerResult.forEach(orig -> {
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

	public List<Customer> retrieveCustomerByname(String name) {
		Iterable<CustomerEntity> customerEntity = customerRepository.getAllByName(name);
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

	@Autowired
	CustomRestClientTemplate restClient;

	public Optional<Customer> createCustomer(Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (icustomerRepository.existsById(request.getPersonalId())) {
			return customerOptional;
		} else {
			CustomerEntity persit = new CustomerEntity();
			getPropertyUtilsBean().copyProperties(persit, request);
			persit = icustomerRepository.save(persit);
			customerOptional = Optional.of(request);
			sendEmail(persit.getPersonalId());
			return customerOptional;
		}
	}

	private void sendEmail(String personalId) {
		try {
			SendEmailRequest sendMailReq = new SendEmailRequest();
			sendMailReq.setPersonalId(personalId);
			restClient.postForEntity(new URI(config.getSendEmailUrl()),
					sendMailReq, SendEmailResponse.class);
		} catch (Exception e) {
			//TODO something
			log.error(e.getMessage(),e);
		}
	}

	public Optional<Customer> updateCustomer(String personalId, Customer request) throws Exception {
		Optional<Customer> customerOptional = Optional.empty();
		if (!icustomerRepository.existsById(personalId)) {
			return customerOptional;
		} else {
			deleteCustomer(personalId);
			CustomerEntity persit = new CustomerEntity();
			getPropertyUtilsBean().copyProperties(persit, request);
			persit = icustomerRepository.save(persit);
			customerOptional = Optional.of(request);
			return customerOptional;
		}

	}

	public boolean deleteCustomer(String personalId) {
		if (!icustomerRepository.existsById(personalId)) {
			return false;
		} else {
			icustomerRepository.deleteById(personalId);
			return true;
		}
	}

	public Optional<Customer> retrieveCustomer(String personalId) {
		Optional<Customer> customerOptional = Optional.empty();
		if (!icustomerRepository.existsById(personalId)) {
			return customerOptional;
		} else {
			Optional<CustomerEntity> entityObj = icustomerRepository.findById(personalId);
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

	public ResultList<Customer> retrieveCustomerBynamePagination(String name, Integer pageNumber, Integer pageSize) {
		ResultList<CustomerEntity> customerEntity = customerRepository.getAllByNamePagination(name, pageNumber,
				pageSize);
		List<Customer> returnList = new ArrayList<Customer>();
		customerEntity.getResult().forEach(orig -> {
			Customer dest = new Customer();
			try {
				getPropertyUtilsBean().copyProperties(dest, orig);
				returnList.add(dest);
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
				log.error(e.getMessage(), e);
			}
		});
		ResultList<Customer> resultList = new ResultList<Customer>();
		resultList.setResult(returnList);
		resultList.setPageNumber(customerEntity.getPageNumber());
		resultList.setTotal(customerEntity.getTotal());
		return resultList;
	}

}
