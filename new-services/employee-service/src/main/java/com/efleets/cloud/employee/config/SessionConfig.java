package com.efleets.cloud.employee.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@Configuration
@Profile("!local")
@EnableRedisHttpSession
public class SessionConfig
{
  @Value("${spring.redis.host:poc2-ec-redis-0001-001.zripd1.0001.use2.cache.amazonaws.com}")
  private String redisHost;

  @Value("${spring.redis.port:6379}")
  private int redisPort;

  @Bean
  public LettuceConnectionFactory connectionFactory()
  {
    return new LettuceConnectionFactory(redisHost, redisPort);
  }
}