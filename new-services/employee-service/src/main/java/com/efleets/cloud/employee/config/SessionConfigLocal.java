package com.efleets.cloud.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.config.annotation.web.http.EnableSpringHttpSession;

@Configuration
@Profile("local")
@EnableSpringHttpSession
public class SessionConfigLocal {

	@Bean
    public MapSessionRepository sessionRepository() {
        return new MapSessionRepository();
    }
}
