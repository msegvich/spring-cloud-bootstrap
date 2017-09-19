package com.slalom.cloud.adapter.soap;

import static com.slalom.cloud.adapter.soap.Constants.NAMESPACE_URI;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.slalom.cloud.legacy.users.adapter.services.AdapterService;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.CreateUserRequest;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.CreateUserResponse;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.DeleteUserRequest;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.DeleteUserResponse;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.FindAllRequest;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.FindAllResponse;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.FindUserRequest;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.FindUserResponse;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.RS;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.UpdateUserRequest;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.UpdateUserResponse;
import com.slalom.cloud.legacy.users.adapter.soap.jaxb.User;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Endpoint
public class LegacyClientEndpoint
{
  @Autowired
  private AdapterService adapterService;

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "createUserRequest")
  @ResponsePayload
  public CreateUserResponse handleCreateRQ(@RequestPayload CreateUserRequest createUserRequest)
  {
    Collection<String> messages = new ArrayList<>();
    User user = createUserRequest.getUser();

    // Validate some arbitrary value
    if (StringUtils.isEmpty(user.getLastName()))
    {
      messages.add("Missing attribute: |lastName|");
    }

    // Process rq
    CreateUserResponse result = new CreateUserResponse();

    // call rest
    long id = adapterService.create(user);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);
    result.setId(id);

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

    adapterService.delete(deleteUserRequest.getId());

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
    if (id <= 0L)
    {
      messages.add("Missing attribute: |id|");
    }

    // Process rq
    FindUserResponse result = new FindUserResponse();
    User user = adapterService.get(id);

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);
    result.getUser().add(user);

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findAllRequest")
  @ResponsePayload
  public FindAllResponse handleFindAllRQ(@RequestPayload FindAllRequest findAllRequest)
  {
    Collection<String> messages = new ArrayList<>();

    // Process rq
    FindAllResponse result = new FindAllResponse();

    Collection<User> usersFromDb = adapterService.getAll(); 

    // Build rs
    RS rs = buildResponse(messages);
    result.setResponse(rs);
    result.getUser().addAll(usersFromDb);

    return result;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
  @ResponsePayload
  @HystrixCommand(fallbackMethod = "handleUpdateRQFallback")
  public UpdateUserResponse handleUpdateRQ(@RequestPayload UpdateUserRequest updateUserRequest)
  {
    Collection<String> messages = new ArrayList<>();
    User user = updateUserRequest.getUser();

    // Validate some arbitrary value
    if (StringUtils.isEmpty(user.getLastName()))
    {
      messages.add("Missing attribute: |lastName|");
    }

    // Process rq
    UpdateUserResponse result = new UpdateUserResponse();

    adapterService.create(user);

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

  private long handleCreateRQFallback(User user)
  {

    return -1l;
  }

  private DeleteUserResponse handleDeleteRQFallback(DeleteUserRequest deleteUserRequest)
  {
    DeleteUserResponse result = new DeleteUserResponse();

    RS rs = new RS();
    rs.setStatus(false);
    rs.setMessage("Fall back response");
    result.setResponse(rs);

    return result;
  }

  private FindUserResponse handleFindRQFallback(FindUserRequest findUserRequest)
  {
    FindUserResponse result = new FindUserResponse();

    RS rs = new RS();
    rs.setStatus(false);
    rs.setMessage("Fall back response");
    result.setResponse(rs);

    return result;
  }

  private FindAllResponse handleFindAllRQFallback(FindAllRequest findAllRequest)
  {
    FindAllResponse result = new FindAllResponse();

    RS rs = new RS();
    rs.setStatus(false);
    rs.setMessage("Fall back response");
    result.setResponse(rs);

    return result;
  }

  private UpdateUserResponse handleUpdateRQFallback(UpdateUserRequest updateUserRequest)
  {
    UpdateUserResponse result = new UpdateUserResponse();

    RS rs = new RS();
    rs.setStatus(false);
    rs.setMessage("Fall back response");
    result.setResponse(rs);

    return result;
  }
}
