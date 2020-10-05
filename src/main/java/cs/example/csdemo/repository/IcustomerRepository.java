package cs.example.csdemo.repository;

import org.springframework.data.repository.CrudRepository;

import cs.example.csdemo.entity.CustomerEntity;

public interface IcustomerRepository extends CrudRepository<CustomerEntity, String>{

}
