package com.nt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.nt.model.UserInfo;
import com.nt.service.IUserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private IUserService service;
	
	@GetMapping("/register")
	public String showRegisterPage() {
		return "UserRegisterPage";
	}
	
	@PostMapping("/register")
	public String registerUser(
				@ModelAttribute("userInfo") UserInfo userInfo,
				Model map
			) 
	{
		Integer userId = service.registerUser(userInfo);
		map.addAttribute("message","USER '"+userId+"' REGISTER SUCCESSFULLY!");
		return "UserRegisterPage";
	}
	
	@GetMapping("/showLogin")
	public String showLogin() {
		return "UserLoginPage";
	}
}