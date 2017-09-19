package com.slalom.legacy.cloud.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.slalom.legacy.cloud.model.Greeting;

@RestController
public class GreetingController {

    private static final String template = "Hello from %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/hello")
    public Greeting greeting() {
      String host = System.getenv("HOSTNAME");
      if (null == host) {
        host = "HOSTNAME environment variable not set!";
      }
      return new Greeting(counter.incrementAndGet(), String.format(template, host));
    }
}