package com.efleets.cloud.demo.dao;

import java.util.Collection;

import com.efleets.cloud.demo.domain.User;

public interface UserDao
{
  void create(User user);

  void update(User user);

  User read(long id);

  void delete(long id);

  Collection<User> readAll();

  int deleteAll();
}
