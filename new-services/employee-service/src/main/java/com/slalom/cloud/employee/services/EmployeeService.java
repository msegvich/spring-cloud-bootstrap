package com.slalom.cloud.employee.services;

import java.util.Collection;

import com.slalom.cloud.employee.models.Employee;


public interface EmployeeService {
	void create(Employee Employee);

	void update(Employee Employee);

	Employee read(long id);

	@Deprecated
	Employee readLegacy(long id);

	void delete(long id);

	Collection<Employee> readAll();

	int deleteAll();
}
