package com.slalom.cloud.adapter.soap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RefreshScope
public class Constants
{
  public static final String NAMESPACE_URI = "http://slalom.com/cloud/schema";
  public static final String LOCATION_URI = "/ws";

  @Value("${employeeService.servicePath}")
  private String EMPLOYEE_SERVICE_PATH;

}
