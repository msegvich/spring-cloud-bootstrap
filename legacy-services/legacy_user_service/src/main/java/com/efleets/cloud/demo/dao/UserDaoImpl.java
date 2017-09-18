package com.efleets.cloud.demo.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.efleets.cloud.demo.domain.User;

@Repository
public class UserDaoImpl implements UserDao
{
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public void create(User user)
  {
    this.entityManager.persist(user);
  }

  @Override
  public void update(User user)
  {
    this.entityManager.merge(user);
  }

  @Override
  public User read(long id)
  {
    User result = this.entityManager.find(User.class, id);

    return result;
  }

  @Override
  public void delete(long id)
  {
    User user = read(id);

    if (user != null)
    {
      this.entityManager.remove(user);
    }
  }

  @Override
  public Collection<User> readAll()
  {
    Collection<User> result = this.entityManager.createQuery("select x from " + User.class.getSimpleName() + " x").getResultList();

    return result;
  }

  @Override
  public int deleteAll()
  {
    int result = this.entityManager.createQuery("delete from " + User.class.getSimpleName()).executeUpdate();

    return result;
  }
}
