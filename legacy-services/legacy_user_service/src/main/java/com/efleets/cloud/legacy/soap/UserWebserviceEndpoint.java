package com.efleets.cloud.legacy.soap;

import static com.efleets.cloud.legacy.soap.Constants.NAMESPACE_URI;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.efleets.cloud.demo.domain.User;
import com.efleets.cloud.demo.service.UserService;
import com.efleets.cloud.schema.CreateUserRequest;
import com.efleets.cloud.schema.CreateUserResponse;
import com.efleets.cloud.schema.DeleteUserRequest;
import com.efleets.cloud.schema.DeleteUserResponse;
import com.efleets.cloud.schema.FindAllRequest;
import com.efleets.cloud.schema.FindAllResponse;
import com.efleets.cloud.schema.FindUserRequest;
import com.efleets.cloud.schema.FindUserResponse;
import com.efleets.cloud.schema.RS;
import com.efleets.cloud.schema.UpdateUserRequest;
import com.efleets.cloud.schema.UpdateUserResponse;

@Endpoint
public class UserWebserviceEndpoint
{
  private UserService userService;

  @Autowired(required = true)
  public UserWebserviceEndpoint(UserService userService)
  {
    this.userService = userService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
  @ResponsePayload
  public CreateUserResponse handleCreateRQ(@RequestPayload CreateUserRequest createUserRequest)
  {
    Collection<String> messages = new ArrayList<>();
    com.efleets.cloud.schema.User user = createUserRequest.getUser();

    // Validate some arbitrary value
    if (StringUtils.isEmpty(user.getLastName()))
    {
      messages.add("Missing attribute: |lastName|");
    }

    // Process rq
    CreateUserResponse result = new CreateUserResponse();

    com.efleets.cloud.schema.User userFromRQ = createUserRequest.getUser();

    User userToStore = new User();
    BeanUtils.copyProperties(userFromRQ, userToStore);

    this.userService.create(userToStore);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);

    result.setId(userToStore.getId());

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
  @ResponsePayload
  public DeleteUserResponse handleDeleteRQ(@RequestPayload DeleteUserRequest deleteUserRequest)
  {
    Collection<String> messages = new ArrayList<>();

    long id = deleteUserRequest.getId();

    // Validate some arbitrary value
    if (id <= 0L)
    {
      messages.add("Missing attribute: |id|");
    }

    // Process rq
    DeleteUserResponse result = new DeleteUserResponse();

    this.userService.delete(id);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findUserRequest")
  @ResponsePayload
  public FindUserResponse handleFindRQ(@RequestPayload FindUserRequest findUserRequest)
  {
    Collection<String> messages = new ArrayList<>();

    long id = findUserRequest.getId();

    // Validate some arbitrary value
    if (id > 0L)
    {
      messages.add("Missing attribute: |id|");
    }

    // Process rq
    FindUserResponse result = new FindUserResponse();

    User userFromDb = this.userService.read(id);
    com.efleets.cloud.schema.User userForRS = new com.efleets.cloud.schema.User();
    BeanUtils.copyProperties(userFromDb, userForRS);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);
    result.getUser().add(userForRS);

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllRequest")
  @ResponsePayload
  public FindAllResponse handleFindAllRQ(@RequestPayload FindAllRequest findAllRequest)
  {
    Collection<String> messages = new ArrayList<>();

    // Process rq
    FindAllResponse result = new FindAllResponse();

    Collection<User> usersFromDb = this.userService.readAll();
    Collection<com.efleets.cloud.schema.User> usersForRS = result.getUser();

    for (User userFromDb : usersFromDb)
    {
      com.efleets.cloud.schema.User userForRS = new com.efleets.cloud.schema.User();

      BeanUtils.copyProperties(userFromDb, userForRS);
      usersForRS.add(userForRS);
    }

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
  @ResponsePayload
  public UpdateUserResponse handleUpdateRQ(@RequestPayload UpdateUserRequest updateUserRequest)
  {
    Collection<String> messages = new ArrayList<>();
    com.efleets.cloud.schema.User user = updateUserRequest.getUser();

    // Validate some arbitrary value
    if (StringUtils.isEmpty(user.getLastName()))
    {
      messages.add("Missing attribute: |lastName|");
    }

    // Process rq
    UpdateUserResponse result = new UpdateUserResponse();

    com.efleets.cloud.schema.User userFromRQ = updateUserRequest.getUser();

    User userToStore = new User();
    BeanUtils.copyProperties(userFromRQ, userToStore);

    this.userService.update(userToStore);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);

    return result;
  }

  private RS buildResponse(Collection<String> messages)
  {
    RS result = new RS();

    result.setStatus(messages.isEmpty());

    if (!result.isStatus())
    {
      String messagesFromList = String.join("; ", messages);

      result.setMessage(messagesFromList);
    }

    return result;
  }

}
