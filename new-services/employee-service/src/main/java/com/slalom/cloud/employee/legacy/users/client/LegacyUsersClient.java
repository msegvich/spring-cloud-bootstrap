package com.slalom.cloud.employee.legacy.users.client;

import com.slalom.cloud.legacy.users.client.jaxb.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class LegacyUsersClient extends WebServiceGatewaySupport {
	@Value("${legacy_user_endpoint}")
	String legacy_endpoint;
	@Value("${legacy_user_schema}")
	String legacy_schema;

	public FindUserResponse findUser(long id) {
		FindUserRequest request = new FindUserRequest();
		request.setId(id);

		FindUserResponse response = (FindUserResponse) getWebServiceTemplate()
				.marshalSendAndReceive(legacy_endpoint,
						request,
						new SoapActionCallback(legacy_schema + "/findUserRequest"));

		return response;
	}

	public FindAllResponse findAllUsers() {
		FindAllRequest request = new FindAllRequest();

		FindAllResponse response = (FindAllResponse) getWebServiceTemplate()
				.marshalSendAndReceive(legacy_endpoint,
						request,
						new SoapActionCallback(legacy_schema + "/findAllRequest"));

		return response;
	}
	
	public DeleteUserResponse deleteUser(long id) {
		DeleteUserRequest request = new DeleteUserRequest();
		request.setId(id);

		DeleteUserResponse response = (DeleteUserResponse) getWebServiceTemplate()
				.marshalSendAndReceive("http://localhost:8060/cloud-demo/ws", 
						request,
						new SoapActionCallback("http://slalom.com/cloud/schema/UserPort/deleteUserRequest"));

		return response;
	}
}
