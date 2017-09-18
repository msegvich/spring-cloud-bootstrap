package com.efleets.cloud.legacy.users.adapter.services;

import static com.efleets.cloud.legacy.users.adapter.AdapterUtils.mapEmployee;
import static com.efleets.cloud.legacy.users.adapter.AdapterUtils.mapUser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import com.efleets.cloud.employee.models.Employee;
import com.efleets.cloud.legacy.users.adapter.soap.jaxb.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;


public class RestTemplateAdapterServiceImpl implements AdapterService {

	@LoadBalanced
	@Autowired
	private RestTemplate restTemplate;

	@Value("${employeeService.uri}")
	private String EMPLOYEE_SERVICE_URI;

	@Value("${employeeService.servicePath}")
	private String EMPLOYEE_SERVICE_PATH;
	  
	@Override
	@HystrixCommand
	public long create(User user) {
		Employee request = mapEmployee(user);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");
	    long result = 0L;
	    Employee employee = null;
	    try
	    {
	      employee = this.restTemplate.exchange(
	    		  EMPLOYEE_SERVICE_URI + EMPLOYEE_SERVICE_PATH,
	    		  HttpMethod.POST,
	    		  new HttpEntity<Employee>(request, headers),
	    		  Employee.class
	    		  ).getBody();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }

	    if (employee != null)
	    {
	      result = employee.getId();
	    }

	    return result;
	}

	@Override
	@HystrixCommand
	public void delete(long id) {
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
	    
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");

	    this.restTemplate.exchange(
	    		EMPLOYEE_SERVICE_URI + EMPLOYEE_SERVICE_PATH + "/{id}",
	    		HttpMethod.DELETE,
	    		new HttpEntity<String>(null, headers),
	    		String.class,
	    		parameters);
	}

	@Override
	@HystrixCommand
	public User get(long id) {
		Map<String, Object> parameters = new HashMap<>();
	    parameters.put("id", id);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");

	    User result = null;

	    Employee employee = this.restTemplate.exchange(
	    		EMPLOYEE_SERVICE_URI + EMPLOYEE_SERVICE_PATH + "/{id}/legacy",
	    		HttpMethod.GET,
	    		new HttpEntity<Employee>(null, headers),
	    		Employee.class,
	    		parameters).getBody();

	    if (employee != null)
	    {
	      result = mapUser(employee);
	    }

	    return result;
	}

	@Override
	@HystrixCommand
	public Collection<User> getAll() {
		Collection<User> result = new ArrayList<>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic YWRtaW46cGFzc3dvcmQ=");

	    Employee[] employees = this.restTemplate.exchange(
	    		EMPLOYEE_SERVICE_URI + EMPLOYEE_SERVICE_PATH + "/all",
	    		HttpMethod.GET,
	    		new HttpEntity<Employee[]>(null, headers),
	    		Employee[].class).getBody();

	    for (Employee employee : employees)
	    {
	      result.add(mapUser(employee));
	    }

	    return result;
	}

}
