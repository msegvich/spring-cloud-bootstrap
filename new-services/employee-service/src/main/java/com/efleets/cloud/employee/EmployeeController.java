package com.efleets.cloud.employee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.efleets.cloud.employee.exceptions.CascadeException;
import com.efleets.cloud.employee.exceptions.NotFoundException;
import com.efleets.cloud.employee.models.Employee;
import com.efleets.cloud.employee.services.EmployeeService;

@RestController
@RequestMapping("/employee")
public class EmployeeController {
	  private static final Log LOG = LogFactory.getLog(EmployeeController.class);
	  
	  @Autowired
	  private EmployeeService employeeService;
	  
	  @RequestMapping(value="all", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Collection<Employee> readAll() {
		  Collection<Employee> employees = null;
		  
		  try {
			  employees = this.employeeService.readAll();
		  } catch (Exception e) {
			  LOG.error(e);
			  employees = new ArrayList<Employee>();
		  }
		  
		  return employees;
	  }
	  
	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value="all", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Map<String, Integer> deleteAll() {
		  Map<String, Integer> result = new HashMap<String, Integer>();
		  result.put("numberDeleted", this.employeeService.deleteAll());
		  return result;
	  }

	  @RequestMapping(value="{id}", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Employee read(@PathVariable(name="id", required=true) Long id) throws NotFoundException {
		  Employee employee = null;
		  
		  try {
			  employee = this.employeeService.read(id);
		  } catch (Exception e) {
			  LOG.error(e);
			  throw new NotFoundException("No employee with id " + id);
		  }
		  
		  return employee;
	  }

	  @Deprecated
	  @RequestMapping(value="{id}/legacy", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Employee readLegacy(@PathVariable(name="id", required=true) Long id) throws NotFoundException {
		  Employee employee = null;

		  try {
			  employee = this.employeeService.readLegacy(id);
		  } catch (Exception e) {
			  LOG.error(e);
			  throw new NotFoundException("No legacy employee with id " + id);
		  }

		  return employee;
	  }

	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value="{id}", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	  public void delete(@PathVariable(name="id", required=true) Long id) throws NotFoundException, CascadeException {
		  try {
			  this.employeeService.delete(id);
		  } catch (DataIntegrityViolationException e) {
			  throw new CascadeException(id);
		  } catch (Exception e) {
			  throw new NotFoundException("Employee with id " + id + " does not exist.");
		  }
	  }

	  @RequestMapping(value="", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	  @PreAuthorize("hasRole('ROLE_ADMIN')")
	  public Employee create(@RequestBody Employee employee) {
		  if (null == employee.getHireDate())
			  employee.setHireDate(new Date());
		  if (null == employee.getId())
			  this.employeeService.create(employee);
		  else {
			  this.employeeService.update(employee);
		  }
		  return employee;
	  }

	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value="{id}/directReports", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Collection<Employee> addDirectReports(@PathVariable(name="id", required=true) Long manId, @RequestBody Collection<Long> ids) {
		  Employee manager = this.employeeService.read(manId);
		  Collection<Employee> directReports = new ArrayList<Employee>();
		  for (Long id: ids) {
			  Employee report = this.employeeService.read(id);
			  report.setManager(manager);
			  this.employeeService.update(report);
			  directReports.add(report);
		  }
		  
		  return directReports;
	  }
	  
	  @RequestMapping(value="{id}/directReports", method=RequestMethod.GET, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Collection<Employee> getDirectReports(@PathVariable(name="id", required=true) Long manId) {
		  Employee manager = this.employeeService.read(manId);
		  
		  return manager.getDirectReports();
	  }

	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value="{id}/directReports", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Collection<Employee> removeDirectReports(@PathVariable(name="id", required=true) Long manId, @RequestBody Collection<Long> ids) {
		  Employee manager = this.employeeService.read(manId);
		  Collection<Employee> directReports = new ArrayList<Employee>();
		  for (Employee directReport: manager.getDirectReports()) {
			  if (ids.contains(directReport.getId())) {
				  directReport.setManager(null);
				  directReports.add(directReport);
			  }
		  }
		  return directReports;
	  }

	  @PreAuthorize("hasRole('ADMIN')")
	  @RequestMapping(value="{id}/directReports/all", method=RequestMethod.DELETE, produces=MediaType.APPLICATION_JSON_VALUE)
	  public Collection<Employee> removeDirectReports(@PathVariable(name="id", required=true) Long manId) {
		  Employee manager = this.employeeService.read(manId);
		  Collection<Employee> directReports = new ArrayList<Employee>();
		  for (Employee directReport: manager.getDirectReports()) {
			  directReport.setManager(null);
			  directReports.add(directReport);
		  }
		  return directReports;
	  }
}
