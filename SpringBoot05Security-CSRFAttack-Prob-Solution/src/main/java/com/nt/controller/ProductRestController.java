package com.nt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ProductRestController {
	
	@GetMapping("/loan")
	public String showHome() {
		return "HomePage";
	}
}
