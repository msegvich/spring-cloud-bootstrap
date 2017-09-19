package com.slalom.cloud.employee.services;

import java.util.ArrayList;
import java.util.Collection;

import com.slalom.cloud.employee.legacy.users.client.LegacyUsersClient;
import com.slalom.cloud.employee.models.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.slalom.cloud.legacy.users.client.jaxb.FindUserResponse;
import com.slalom.cloud.legacy.users.client.jaxb.User;

@Service
public class LegacyConnectServiceImpl implements LegacyConnectService {

	@Autowired
    LegacyUsersClient legacyUsersClient;

	@Override
	public Employee getUser(long id) {
		Employee emp = null;
		
		FindUserResponse resp = this.legacyUsersClient.findUser(id);
		if (resp.getUser().size() > 0) {
			emp = convertUserToEmployee(resp.getUser().get(0), id);
		}
		
		return emp;
	}

	@Override
	public Collection<Employee> getAllUsers() {
		Collection<Employee> employees = new ArrayList<Employee>();
		Collection<User> users = this.legacyUsersClient.findAllUsers().getUser();
		for (User user: users) {
			employees.add(convertUserToEmployee(user, -1l));
		}
		return employees;
	}
	
	@Override
	public void deleteUser(long id) {
		this.legacyUsersClient.deleteUser(id);
	}
	
	private static Employee convertUserToEmployee(User user, long id) {
		Employee emp = new Employee();
		emp.setBranch(user.getBranch());
		emp.setEmail(user.getEmail());
		emp.setFirstName(user.getFirstName());
		emp.setGroup(user.getGroup());
		if (null != user.getHireDate())
			emp.setHireDate(user.getHireDate().toGregorianCalendar().getTime());
		emp.setLastName(user.getLastName());
		emp.setLogonId(user.getLogonId());
		emp.setMiddleInitial(user.getMiddleInitial());
		emp.setPhone(user.getPhone());
		if (null != user.getTerminationDate())
			emp.setTerminationDate(user.getTerminationDate().toGregorianCalendar().getTime());
		emp.setTitle(user.getTitle());
		emp.setLegacyId(id);
		return emp;
	}

}
