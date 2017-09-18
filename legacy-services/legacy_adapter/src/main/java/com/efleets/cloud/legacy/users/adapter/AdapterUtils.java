package com.efleets.cloud.legacy.users.adapter;

import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.efleets.cloud.employee.models.Employee;
import com.efleets.cloud.legacy.users.adapter.soap.jaxb.User;

public class AdapterUtils {
	
	public static Employee mapEmployee(User user)
	  {
	    Employee result = new Employee();

	    result.setBranch(user.getBranch());
	    result.setEmail(user.getEmail());
	    result.setFirstName(user.getFirstName());
	    result.setGroup(user.getGroup());
	    result.setHireDate(getDate(user.getHireDate()));
	    result.setLastName(user.getLastName());
	    result.setLogonId(user.getLogonId());
	    result.setMiddleInitial(user.getMiddleInitial());
	    result.setPhone(user.getPhone());
	    result.setTerminationDate(getDate(user.getTerminationDate()));
	    result.setTitle(user.getTitle());

	    return result;
	  }

	  public static User mapUser(Employee employee)
	  {
	    User result = new User();

	    result.setBranch(employee.getBranch());
	    result.setEmail(employee.getEmail());
	    result.setFirstName(employee.getFirstName());
	    result.setGroup(employee.getGroup());
	    result.setHireDate(getXMLGregorianCalendar(employee.getHireDate()));
	    result.setLastName(employee.getLastName());
	    result.setLogonId(employee.getLogonId());
	    result.setMiddleInitial(employee.getMiddleInitial());
	    result.setPhone(employee.getPhone());
	    result.setTerminationDate(getXMLGregorianCalendar(employee.getTerminationDate()));
	    result.setTitle(employee.getTitle());

	    return result;
	  }

	  public static Date getDate(XMLGregorianCalendar xmlGregorianCalendar)
	  {
	    Date result = null;

	    try
	    {
	      result = xmlGregorianCalendar.toGregorianCalendar().getTime();
	    }
	    catch (Exception exception)
	    {
	      // Log exception
	    }

	    return result;
	  }

	  public static XMLGregorianCalendar getXMLGregorianCalendar(Date date)
	  {
	    XMLGregorianCalendar result = null;

	    try
	    {
	      GregorianCalendar gregorianCalendar = new GregorianCalendar();
	      gregorianCalendar.setTime(date);
	      result = DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
	    }
	    catch (Exception exception)
	    {
	      // Log exception
	    }

	    return result;
	  }

}
