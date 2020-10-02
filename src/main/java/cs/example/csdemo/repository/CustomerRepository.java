package cs.example.csdemo.repository;

import org.springframework.data.repository.CrudRepository;

import cs.example.csdemo.entity.CustomerEntity;

public interface CustomerRepository extends CrudRepository<CustomerEntity, String>{

}
