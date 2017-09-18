package com.efleets.cloud.legacy.users.adapter.services;

import java.util.Collection;

import com.efleets.cloud.legacy.users.adapter.soap.jaxb.User;

public interface AdapterService {

	public long create(User user);
	
	public void delete(long id);
	
	public User get(long id);
	
	public Collection<User> getAll();
}
