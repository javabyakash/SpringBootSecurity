package com.nt.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BankOperationsController {
	
	@GetMapping("/")
	public String showHome() {
		return "home";
	}
	
	@GetMapping("/offers")
	public String showOffers() {
		return "offer";
	} 
	
	@GetMapping("/balance")
	public String showBalance(Model map) {
		int balance = Math.abs(new Random().nextInt(9999));
		map.addAttribute("balance",balance);
		return "balance";
	} 
	
	@GetMapping("/loan")
	public String approveLoan() {
		return "loan";
	}
	
	@GetMapping("/denied")
	public String accessDenied() {
		return "denied";
	}
}
