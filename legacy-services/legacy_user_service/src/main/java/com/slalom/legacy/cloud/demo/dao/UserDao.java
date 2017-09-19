package com.slalom.legacy.cloud.demo.dao;

import java.util.Collection;

import com.slalom.legacy.cloud.demo.domain.User;

public interface UserDao
{
  void create(User user);

  void update(User user);

  User read(long id);

  void delete(long id);

  Collection<User> readAll();

  int deleteAll();
}
