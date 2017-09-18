package com.efleets.cloud.legacy.restfull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.efleets.cloud.demo.domain.User;
import com.efleets.cloud.demo.service.UserService;

@RestController
@EnableAutoConfiguration
public class UserWebserviceProcessor
{
  private static final Log LOG = LogFactory.getLog(UserWebserviceProcessor.class);

  @Autowired(required = true)
  private UserService userService;

  @RequestMapping(value = "/l/create", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> create()
  {
    Map<String, String> result = new HashMap<>();

    User user = null;

    try
    {
      user = User.buildUser();
      this.userService.create(user);
    }
    catch (Exception exception)
    {
      LOG.error(exception.getMessage(), exception);
    }

    result.put("result", "Record created: |" + user + "|");

    return result;
  }

  @RequestMapping(value = "/l/readAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, Collection<User>> readAll()
  {
    Map<String, Collection<User>> result = new HashMap<>();

    Collection<User> users = null;

    try
    {
      users = this.userService.readAll();
    }
    catch (Exception exception)
    {
      LOG.error(exception.getMessage(), exception);
    }

    result.put("result", users);

    return result;
  }

  @RequestMapping(value = "/l/read", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, User> read(long id)
  {
    Map<String, User> result = new HashMap<>();

    User user = null;

    try
    {
      user = this.userService.read(id);
    }
    catch (Exception exception)
    {
      LOG.error(exception.getMessage(), exception);
    }

    result.put("result", user);

    return result;
  }

  @RequestMapping(value = "/l/deleteAll", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> deleteAll()
  {
    Map<String, String> result = new HashMap<>();

    int numberOfrecords = -1;

    try
    {
      numberOfrecords = this.userService.deleteAll();
    }
    catch (Exception exception)
    {
      LOG.error(exception.getMessage(), exception);
    }

    result.put("result", "Number of records deleted: |" + numberOfrecords + "|");

    return result;
  }

  @RequestMapping(value = "/l/delete", produces = MediaType.APPLICATION_JSON_VALUE)
  public Map<String, String> delete(long id)
  {
    Map<String, String> result = new HashMap<>();

    this.userService.delete(id);

    result.put("result", "Record deleted");

    return result;
  }

  public void setUserService(UserService userService)
  {
    this.userService = userService;
  }
}
