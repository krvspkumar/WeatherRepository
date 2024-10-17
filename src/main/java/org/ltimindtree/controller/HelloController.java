package org.ltimindtree.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	@GetMapping("/")
	public String hello() {
		return "Hello from Spring Boot on AWS Lambda!";
	}

	@GetMapping("/{name}")
	public String helloName(@PathVariable String name) {
		return "Hello from Spring Boot on AWS Lambda!" + " " + name;
	}

}
