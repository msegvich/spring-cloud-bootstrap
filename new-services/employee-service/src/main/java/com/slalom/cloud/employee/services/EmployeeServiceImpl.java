package com.slalom.cloud.employee.services;

import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import com.slalom.cloud.employee.dao.EmployeeDao;
import com.slalom.cloud.employee.models.Employee;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService {
	private static final Log LOG = LogFactory.getLog(EmployeeServiceImpl.class);

	@Autowired(required=true)
	private EmployeeDao employeeDao;

	@Autowired(required=true)
	private LegacyConnectService legacyService;
	
	@Override
	public void create(Employee employee) {
		this.employeeDao.createEmployee(employee);
	}

	@Override
	public void update(Employee employee) {
		this.employeeDao.update(employee);
	}

	@Override
	public Employee read(long id) {
		Employee emp = null;

		emp = this.employeeDao.read(id);
		if (null == emp) {
			emp = getLegacy(id);
			this.create(emp);
		}

		return emp;
	}

	@Override
	@Deprecated
	public Employee readLegacy(long id) {
		Employee emp = getLegacy(id);
		if (null != emp) {
			this.create(emp);
		}

		return emp;
	}

	@HystrixCommand(fallbackMethod = "readLegacyFallback")
	@Deprecated
	private Employee getLegacy(long id) {
		Employee emp = null;

		try
		{
			emp = this.employeeDao.readLegacy(id);
			if (null == emp) {
				emp = this.legacyService.getUser(id);
			}
		}
		catch(Exception ex)
		{
			LOG.error("Failed to find legacy user", ex);
			throw ex;
		}
		return emp;
	}

	@Override
	public void delete(long id) {
		//Remove from legacy
		Long legacyId = this.employeeDao.read(id).getLegacyId();
		this.employeeDao.delete(id);
		deleteLegacy(legacyId);
	}
	
	@Deprecated
	private void deleteLegacy(Long legacyId) {
		if (null != legacyId) {
			this.legacyService.deleteUser(legacyId);
		}
	}

	@HystrixCommand(fallbackMethod = "readAllFallback")
	@Override
	public Collection<Employee> readAll() {
		Collection<Employee> employees = this.employeeDao.readAll();
		Collection<Employee> legacyUsers = this.legacyService.getAllUsers();
		return joinUserLists(employees, legacyUsers);
	}

	@Override
	public int deleteAll() {
		return this.employeeDao.deleteAll();
	}

	private static Collection<Employee> joinUserLists(Collection<Employee> nevv, Collection<Employee> legacy) {
		Collection<Employee> employees = new HashSet<Employee>();

		employees.addAll(nevv);
		employees.addAll(legacy);

		return employees;
	}

	private Employee readLegacyFallback(long id){
		Employee ee = new Employee();
		ee.setLogonId("ReadLegacyHystrixHandled for ID: " + Long.toString(id));
		return ee;
	}

	private Collection<Employee> readAllFallback(){
		Employee ee = new Employee();
		ee.setLogonId("ReadAllHystrixHandled");
		Collection<Employee> list = new HashSet<>();
		list.add(ee);
		return list;
	}

}
