package com.slalom.cloud.employee.dao;

import java.util.Collection;

import com.slalom.cloud.employee.models.Employee;

public interface EmployeeDao {

	void createEmployee(Employee emp);
	
	void update(Employee emp);
	
	Employee read(long id);

	@Deprecated
	Employee readLegacy(long id);

	void delete(long id);
	
	Collection<Employee> readAll();
	
	int deleteAll();
}
