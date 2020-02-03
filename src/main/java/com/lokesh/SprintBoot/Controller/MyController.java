package com.lokesh.SprintBoot.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@GetMapping(path="/")
	public String loadHomePage(){
		return "Welcome to My First Sprint WebService Page";
	}
	
	@GetMapping(path="/hello")
	public String hello(){
		return "Hello World";
	}
}
