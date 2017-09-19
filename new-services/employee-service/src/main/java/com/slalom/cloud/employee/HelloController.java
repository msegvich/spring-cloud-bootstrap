package com.slalom.cloud.employee;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

	@RequestMapping(method=RequestMethod.GET)
	public String world() {
		return name("World");
	}
	
	@RequestMapping(value="{name}", method=RequestMethod.GET)
	public String name(@PathVariable(name="name") String name) {
		return "Hello " + name + "!";
	}
}
