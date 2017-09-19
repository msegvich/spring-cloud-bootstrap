package com.slalom.cloud.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.slalom.cloud.legacy.users.adapter.services.AdapterService;
import com.slalom.cloud.legacy.users.adapter.services.FeignAdapterServiceImpl;
import com.slalom.cloud.legacy.users.adapter.services.RestTemplateAdapterServiceImpl;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
public class LegacyAdapterApplication
{
  @Value("${adapterService}")
  private String adapterChoice;
	
  public static void main(String[] args)
  {
    SpringApplication.run(LegacyAdapterApplication.class, args);
  }

  @Bean
  @LoadBalanced
  public RestTemplate restTemplate()
  {
    return new RestTemplate();
  }
  
  @Bean
  public AdapterService adapterService() {
	  if ("feign".equals(adapterChoice)) {
		  return new FeignAdapterServiceImpl();
	  } else {
		  return new RestTemplateAdapterServiceImpl();
	  }
  }
}
