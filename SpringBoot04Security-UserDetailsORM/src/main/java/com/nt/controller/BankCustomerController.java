package com.nt.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bank")
public class BankCustomerController {
	
	@GetMapping("/")
	public String showHome() {
		return "BankHome";
	}
	
	@GetMapping("/balance")
	public String showBalance(Model map) {
		System.out.println("IN BALANCE PAGE");
		map.addAttribute("balance",Math.abs(new Random().nextInt(99999)));
		return "Balance";
	}
	
	@PostMapping("/loan")
	public String loanApprove() {
		return "LoanPage";
	}
	
	@GetMapping("/offer")
	public String showOffers() {
		return "OfferPage";
	}
	
	@GetMapping("/access")
	public String accessDenied() {
		return "AccessDeniedPage";
	}
	
}
