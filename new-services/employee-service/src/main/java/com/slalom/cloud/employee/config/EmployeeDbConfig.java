package com.slalom.cloud.employee.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmployeeDbConfig
{

  @Value("${employee.datasource.url}")
  private String dataSourceUrl;

  @Value("${employee.datasource.username}")
  private String dataSourceUsername;

  @Value("${employee.datasource.password}")
  private String dataSourcePassword;

  @Value("${employee.datasource.driver-class-name}")
  private String dataSourceDriver;

  @Bean
  public DataSource dataSource()
  {
    return DataSourceBuilder.create().url(dataSourceUrl).username(dataSourceUsername).password(dataSourcePassword).driverClassName(dataSourceDriver)
        .build();
  }

}
