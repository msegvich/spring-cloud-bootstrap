package com.slalom.legacy.cloud.demo.service;

import java.util.Collection;

import com.slalom.legacy.cloud.demo.domain.User;

public interface UserService
{
  void create(User user);

  void update(User user);

  User read(long id);

  void delete(long id);

  Collection<User> readAll();

  int deleteAll();
}
