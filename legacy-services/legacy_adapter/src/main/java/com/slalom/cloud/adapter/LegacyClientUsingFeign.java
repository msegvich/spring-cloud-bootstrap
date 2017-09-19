package com.slalom.cloud.adapter;

import java.util.Collection;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.slalom.cloud.employee.models.Employee;

@FeignClient(name = "${legacy.service-name}")
public interface LegacyClientUsingFeign
{
  @RequestMapping(value = "${employeeService.servicePath}", method = RequestMethod.POST)
  Employee create(@RequestHeader("Authorization") String auth, @RequestBody Employee employee);

  @RequestMapping(value = "${employeeService.servicePath}/{id}", method = RequestMethod.DELETE)
  void delete(@RequestHeader("Authorization") String auth, @PathVariable(name = "id", required = true) Long id);

  @RequestMapping(value = "${employeeService.servicePath}/{id}", method = RequestMethod.GET)
  Employee get(@RequestHeader("Authorization") String auth, @PathVariable(name = "id", required = true) Long id);

  @RequestMapping(value = "${employeeService.servicePath}/all", method = RequestMethod.GET)
  Collection<Employee> getAll(@RequestHeader("Authorization") String auth);
}
