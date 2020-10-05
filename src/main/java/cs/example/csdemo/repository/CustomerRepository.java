package cs.example.csdemo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cs.example.csdemo.dto.ResultList;
import cs.example.csdemo.entity.CustomerEntity;

@Repository
public class CustomerRepository {

	// @PersistenceContext บอกให้ JavaEE Container Inject EntityManager มาให้เรา
	// (คล้ายกับ @Autowired แต่ @PersistenceContext จะเป็นของ JPA โดยตรง และมีการ
	// provide config ต่างๆให้เราแล้ว)
	@PersistenceContext()
	private EntityManager em;

	// Use createNamedQuery
	public List<CustomerEntity> getAllByName(String name) {
		/*
		 * ดึงข้อมูลโดยใช้ Method createNamedQuery() โดบ มี parameter 2 ตัวคือ
		 * customer.get-all-by-name : ชื่อของ Query ที่ต้องการเรียกใช้ resultClass:
		 * CustomerEntity.class สำหรับผลลัพธ์ของ Query
		 */
		List<CustomerEntity> result = em.createNamedQuery("customer.get-all-by-name", CustomerEntity.class)
				.setParameter("NAME", "%" + name + "%").getResultList();
		return result;
	}

	// use createQuery
	public ResultList<CustomerEntity> getAllByNamePagination(String name, int pageNumber, int pageSize) {

		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		countQuery.select(criteriaBuilder.count(countQuery.from(CustomerEntity.class)));
		Long count = em.createQuery(countQuery).getSingleResult();
		CriteriaQuery<CustomerEntity> criteriaQuery = criteriaBuilder.createQuery(CustomerEntity.class);
		Root<CustomerEntity> from = criteriaQuery.from(CustomerEntity.class);
		CriteriaQuery<CustomerEntity> select = criteriaQuery.select(from);
		criteriaQuery.orderBy(criteriaBuilder.asc(from.get("personalId")));
		List<Predicate> criteria = new ArrayList<Predicate>();
		Predicate name_codePredicate = criteriaBuilder.like(from.get("name"), "%" + name + "%");
		criteria.add(name_codePredicate);
		select.where(criteria.toArray(new Predicate[] {}));
		TypedQuery<CustomerEntity> typedQuery = em.createQuery(select);
		typedQuery.setFirstResult(pageSize * (pageNumber - 1));
		typedQuery.setMaxResults(pageSize);
		System.out.println("Current page: " + typedQuery.getResultList());
		ResultList<CustomerEntity> resultSet = new ResultList<CustomerEntity>();
		resultSet.setResult(typedQuery.getResultList());
		resultSet.setPageNumber(pageNumber);
		resultSet.setTotal(count);
		return resultSet;
	}
}
