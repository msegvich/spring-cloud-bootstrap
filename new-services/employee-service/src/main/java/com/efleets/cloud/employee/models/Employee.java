package com.efleets.cloud.employee.models;

import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "logon_id", length = 50)
	private String logonId;

	@Column(name = "first_name", length = 30)
	private String firstName;

	@Column(name = "middle_initial", length = 1)
	private String middleInitial;

	@Column(name = "last_name", length = 30)
	private String lastName;

	@Column(name = "phone", length = 24)
	private String phone;

	@Column(name = "email", length = 70)
	private String email;

	@Column(name = "group_number", length = 2)
	private String group;

	@Column(name = "branch", length = 2)
	private String branch;

	@Column(name = "title", length = 20)
	private String title;

	@Column(name = "hire_date")
	private Date hireDate;

	@Column(name = "termination_date")
	private Date terminationDate;

	@ManyToOne
	private Employee manager;

	@JsonIgnore
	@OneToMany(mappedBy="manager")
	private Collection<Employee> directReports;

	@Deprecated
	@JsonIgnore
	@Column(name = "legacyId")
	private Long legacyId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogonId() {
		return logonId;
	}

	public void setLogonId(String logonId) {
		this.logonId = logonId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getHireDate() {
		return hireDate;
	}

	public void setHireDate(Date hireDate) {
		this.hireDate = hireDate;
	}

	public Date getTerminationDate() {
		return terminationDate;
	}

	public void setTerminationDate(Date terminationDate) {
		this.terminationDate = terminationDate;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public Collection<Employee> getDirectReports() {
		return directReports;
	}

	public void setDirectReports(Collection<Employee> directReports) {
		this.directReports = directReports;
	}

	@Deprecated
	public Long getLegacyId() {
		return legacyId;
	}

	@Deprecated
	public void setLegacyId(Long legacyId) {
		this.legacyId = legacyId;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Employee) {
			return o.hashCode() == this.hashCode();
		}
		return false;
	}

	@Override
	public int hashCode() {
		return (this.logonId + this.firstName + this.middleInitial + this.lastName).hashCode();
	}
}
