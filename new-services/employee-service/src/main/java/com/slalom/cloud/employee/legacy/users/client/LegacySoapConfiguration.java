package com.slalom.cloud.employee.legacy.users.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class LegacySoapConfiguration {
	@Value("${legacy_user_endpoint}")
	String legacy_endpoint;
	
	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setContextPath("com.slalom.cloud.legacy.users.client.jaxb");
		return marshaller;
	}
	
	@Bean
	public LegacyUsersClient legacyClient(Jaxb2Marshaller marshaller) {
		LegacyUsersClient client = new LegacyUsersClient();
		client.setDefaultUri(legacy_endpoint);
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}
}
