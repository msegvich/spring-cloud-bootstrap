package com.efleets.cloud.demo.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.efleets.cloud.demo.service.UserService;
import com.efleets.cloud.demo.service.UserServiceImpl;

@Configuration
public class DbConfig
{
  @Bean
  ServletRegistrationBean servletRegistration()
  {
    ServletRegistrationBean result = new ServletRegistrationBean(new WebServlet());
    result.addUrlMappings("/db/*");

    return result;
  }

  @Bean
  UserService userServiceRegistration()
  {
    UserService result = new UserServiceImpl();

    return result;
  }
}
