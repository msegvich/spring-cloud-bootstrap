package com.slalom.cloud.employee.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.slalom.cloud.employee.models.Employee;
import org.springframework.stereotype.Repository;

@Repository
public class EmployeeDaoImpl implements EmployeeDao {
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void createEmployee(Employee emp) {
		this.entityManager.persist(emp);
	}

	@Override
	public void update(Employee emp) {
		this.entityManager.merge(emp);
	}

	@Override
	public Employee read(long id) {
		Employee result = this.entityManager.find(Employee.class, id);
	    return result;
	}

	@Deprecated
	@Override
	public Employee readLegacy(long id) {
		Employee result = null;
		try {
			result = (Employee) this.entityManager.createQuery("select x from " + Employee.class.getSimpleName() + " x where x.legacyId = " + id).getSingleResult();
		} catch (Exception e) {
			result = null;
		}
	    return result;
	}

	@Override
	public void delete(long id) {
		Employee emp = read(id);
		if (null != emp) {
			this.entityManager.remove(emp);
		}
	}

	@Override
	public Collection<Employee> readAll() {
		Collection<Employee> emps = this.entityManager.createQuery("select x from " + Employee.class.getSimpleName() + " x").getResultList();
		return emps;
	}

	@Override
	public int deleteAll() {
		int result = this.entityManager.createQuery("delete from " + Employee.class.getSimpleName()).executeUpdate();

	    return result;
	}

}
