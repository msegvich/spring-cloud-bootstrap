package com.slalom.cloud.employee.services;

import java.util.Collection;

import com.slalom.cloud.employee.models.Employee;

@Deprecated
public interface LegacyConnectService {
	public Employee getUser(long id);
	public Collection<Employee> getAllUsers();
	public void deleteUser(long id);
}
