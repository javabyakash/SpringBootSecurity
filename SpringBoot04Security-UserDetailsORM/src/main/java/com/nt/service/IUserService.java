package com.nt.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.nt.model.UserInfo;

public interface IUserService extends UserDetailsService {
	public Integer registerUser(UserInfo info); 
}
