package com.efleets.cloud.legacy.users.adapter.services;

import static com.efleets.cloud.legacy.users.adapter.AdapterUtils.mapEmployee;
import static com.efleets.cloud.legacy.users.adapter.AdapterUtils.mapUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.efleets.cloud.employee.models.Employee;
import com.efleets.cloud.legacy.users.adapter.LegacyClientUsingFeign;
import com.efleets.cloud.legacy.users.adapter.soap.jaxb.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


public class FeignAdapterServiceImpl implements AdapterService {

	@Autowired
	private LegacyClientUsingFeign legacyClientUsingFeign;

	@Override
	@HystrixCommand
	public long create(User user) {
		Employee request = mapEmployee(user);

	    Employee response = legacyClientUsingFeign.create("Basic YWRtaW46cGFzc3dvcmQ=", request);
	    long result = response.getId();

	    return result;
	}

	@Override
	@HystrixCommand
	public void delete(long id) {
		legacyClientUsingFeign.delete("Basic YWRtaW46cGFzc3dvcmQ=", id);
	}

	@Override
	@HystrixCommand
	public User get(long id) {
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);

	    User result = null;

	    Employee employee = legacyClientUsingFeign.get("Basic YWRtaW46cGFzc3dvcmQ=", id);
	    result = mapUser(employee);

	    return result;
	}

	@Override
	@HystrixCommand
	public Collection<User> getAll() {
		Collection<User> result = new ArrayList<>();

	    Collection<Employee> employees = legacyClientUsingFeign.getAll("Basic YWRtaW46cGFzc3dvcmQ=");

	    for (Employee employee : employees)
	    {
	      result.add(mapUser(employee));
	    }

	    return result;
	}

}
