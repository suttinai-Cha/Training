package cs.example.csdemo.repository;

import org.springframework.data.repository.CrudRepository;

import cs.example.csdemo.entity.LogEntity;

public interface IlogRepository extends CrudRepository<LogEntity, Integer>{

}
