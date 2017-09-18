package com.efleets.cloud.demo.service;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.efleets.cloud.demo.dao.UserDao;
import com.efleets.cloud.demo.domain.User;

@Transactional
public class UserServiceImpl implements UserService
{
  @Autowired(required = true)
  private UserDao userDao;

  @Override
  public void create(User user)
  {
    this.userDao.create(user);
  }

  @Override
  public void update(User user)
  {
    this.userDao.update(user);
  }

  @Override
  public User read(long id)
  {
    return this.userDao.read(id);
  }

  @Override
  public void delete(long id)
  {
    this.userDao.delete(id);
  }

  @Override
  public Collection<User> readAll()
  {
    return this.userDao.readAll();
  }

  @Override
  public int deleteAll()
  {
    return this.userDao.deleteAll();
  }
}
