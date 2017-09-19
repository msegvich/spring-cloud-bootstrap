package com.slalom.legacy.cloud.demo.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "User")
public class User
{
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column(name = "logon_id")
  private String logonId;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "middle_initial")
  private String middleInitial;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @Column(name = "group_number")
  private String group;

  @Column(name = "branch")
  private String branch;

  @Column(name = "title")
  private String title;

  @Column(name = "hire_date")
  private Date hireDate;

  @Column(name = "termination_date")
  private Date terminationDate;

  public long getId()
  {
    return this.id;
  }

  public void setId(long id)
  {
    this.id = id;
  }

  public String getLogonId()
  {
    return this.logonId;
  }

  public void setLogonId(String logonId)
  {
    this.logonId = logonId;
  }

  public String getFirstName()
  {
    return this.firstName;
  }

  public void setFirstName(String firstName)
  {
    this.firstName = firstName;
  }

  public String getMiddleInitial()
  {
    return this.middleInitial;
  }

  public void setMiddleInitial(String middleInitial)
  {
    this.middleInitial = middleInitial;
  }

  public String getLastName()
  {
    return this.lastName;
  }

  public void setLastName(String lastName)
  {
    this.lastName = lastName;
  }

  public String getPhone()
  {
    return this.phone;
  }

  public void setPhone(String phone)
  {
    this.phone = phone;
  }

  public String getEmail()
  {
    return this.email;
  }

  public void setEmail(String email)
  {
    this.email = email;
  }

  public String getGroup()
  {
    return this.group;
  }

  public void setGroup(String group)
  {
    this.group = group;
  }

  public String getBranch()
  {
    return this.branch;
  }

  public void setBranch(String branch)
  {
    this.branch = branch;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setTitle(String title)
  {
    this.title = title;
  }

  public Date getHireDate()
  {
    return this.hireDate;
  }

  public void setHireDate(Date hireDate)
  {
    this.hireDate = hireDate;
  }

  public Date getTerminationDate()
  {
    return this.terminationDate;
  }

  public void setTerminationDate(Date terminationDate)
  {
    this.terminationDate = terminationDate;
  }

  public static User buildUser()
  {
    User result = new User();

    result.setBranch(RandomStringUtils.randomAlphanumeric(2));
    result.setFirstName(RandomStringUtils.randomAlphabetic(10));
    result.setGroup(RandomStringUtils.randomAlphanumeric(2));
    result.setHireDate(new Date(RandomUtils.nextLong(0, 220752000)));
    result.setLastName(RandomStringUtils.randomAlphabetic(10));
    result.setLogonId(RandomStringUtils.randomAlphanumeric(6));
    result.setMiddleInitial(RandomStringUtils.randomAlphabetic(1));
    result.setPhone(RandomStringUtils.randomNumeric(10));
    result.setTerminationDate(new Date(RandomUtils.nextLong(0, 220752000)));
    result.setEmail(result.getFirstName() + "." + result.getLastName() + "@slalom.test");
    result.setTitle(RandomStringUtils.randomAlphabetic(20));

    return result;
  }

  @Override
  public int hashCode()
  {
    return new HashCodeBuilder().append(this.id).append(this.logonId).append(this.firstName).append(this.middleInitial).append(this.lastName)
        .append(this.phone).append(this.email).append(this.group).append(this.branch).append(this.title).append(this.hireDate)
        .append(this.terminationDate).hashCode();
  }

  @Override
  public boolean equals(Object object)
  {
    if (object == null)
    {
      return false;
    }

    if (this == object)
    {
      return true;
    }

    if (object.getClass() != this.getClass())
    {
      return false;
    }

    User user = (User) object;

    return new EqualsBuilder().append(this.branch, user.getBranch()).append(this.email, user.getEmail()).append(this.firstName, user.getFirstName())
        .append(this.group, user.getGroup()).append(this.hireDate, user.getHireDate()).append(this.id, user.getId())
        .append(this.lastName, user.getLastName()).append(this.logonId, user.getLogonId()).append(this.middleInitial, user.getMiddleInitial())
        .append(this.phone, user.getPhone()).append(this.terminationDate, user.getTerminationDate()).append(this.title, user.getTitle()).isEquals();
  }

  @Override
  public String toString()
  {
    StringBuilder result = new StringBuilder().append("[");

    ReflectionToStringBuilder reflectionToStringBuilder = new ReflectionToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE);

    result.append(reflectionToStringBuilder.toString());

    result.append("]");

    return result.toString();
  }
}