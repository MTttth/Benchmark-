package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	// @GetMapping("/Hello")
	@RequestMapping(value = "/Hello", method = RequestMethod.GET)
	public String hello(String name, String phone_number) {
		return "Hello, " + name + " " + phone_number;
	} 
}
