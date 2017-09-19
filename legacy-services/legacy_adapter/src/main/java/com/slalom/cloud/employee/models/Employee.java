package com.slalom.cloud.employee.models;

import java.util.Collection;
import java.util.Date;

public class Employee
{
  private Long id;

  private String logonId;

  private String firstName;

  private String middleInitial;

  private String lastName;

  private String phone;

  private String email;

  private String group;

  private String branch;

  private String title;

  private Date hireDate;

  private Date terminationDate;

  private Employee manager;

  private Collection<Employee> directReports;

  @Deprecated
  private Long legacyId;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public String getLogonId()
  {
    return logonId;
  }

  public void setLogonId(String logonId)
  {
    this.logonId = logonId;
  }

  public String getFirstName()
  {
    return firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getMiddleInitial()
  {
    return middleInitial;
  }

  public void setMiddleInitial(String middleInitial)
  {
    this.middleInitial = middleInitial;
  }

  public String getLastName()
  {
    return lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getPhone()
  {
    return phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getEmail()
  {
    return email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getGroup()
  {
    return group;
  }

  public void setGroup(String group)
  {
    this.group = group;
  }

  public String getBranch()
  {
    return branch;
  }

  public void setBranch(String branch)
  {
    this.branch = branch;
  }

  public String getTitle()
  {
    return title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Date getHireDate()
  {
    return hireDate;
  }

  public void setHireDate(Date hireDate)
  {
    this.hireDate = hireDate;
  }

  public Date getTerminationDate()
  {
    return terminationDate;
  }

  public void setTerminationDate(Date terminationDate)
  {
    this.terminationDate = terminationDate;
  }

  public Employee getManager()
  {
    return manager;
  }

  public void setManager(Employee manager)
  {
    this.manager = manager;
  }

  public Collection<Employee> getDirectReports()
  {
    return directReports;
  }

  public void setDirectReports(Collection<Employee> directReports)
  {
    this.directReports = directReports;
  }

  @Deprecated
  public Long getLegacyId()
  {
    return legacyId;
  }

  @Deprecated
  public void setLegacyId(Long legacyId)
  {
    this.legacyId = legacyId;
  }

  @Override
  public boolean equals(Object o)
  {
    if (o instanceof Employee)
    {
      return o.hashCode() == this.hashCode();
    }
    return false;
  }

  @Override
  public int hashCode()
  {
    return (this.logonId + this.firstName + this.middleInitial + this.lastName).hashCode();
  }
}
