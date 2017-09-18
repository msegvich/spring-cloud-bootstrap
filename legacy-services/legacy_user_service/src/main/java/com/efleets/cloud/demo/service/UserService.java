package com.efleets.cloud.demo.service;

import java.util.Collection;

import com.efleets.cloud.demo.domain.User;

public interface UserService
{
  void create(User user);

  void update(User user);

  User read(long id);

  void delete(long id);

  Collection<User> readAll();

  int deleteAll();
}
