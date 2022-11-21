package com.nt.service.impl;


import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nt.exception.UserNotFoundException;
import com.nt.model.UserInfo;
import com.nt.repo.UserRepository;
import com.nt.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private BCryptPasswordEncoder encode;
	
	@Override
	public Integer registerUser(UserInfo info) {
		info.setPassword(encode.encode(info.getPassword()));
		UserInfo userId = repo.save(info);
		return userId.getUid();
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> opt = repo.findByEmail(username);
		if(opt.isEmpty())
			throw new UserNotFoundException("USER NOT FOUND WITH GIVEN USERNAME!");
		else {
			UserInfo userInfo = opt.get();
			
			/* 
			 * OLD APPROACH CODE
			 * ------------------------------------------------------- 
			//Converting ROLES into SimpleGrantedAuthority
			Set<GrantedAuthority> roles = new HashSet<>();
			for(String role : userInfo.getRoles()) {
				SimpleGrantedAuthority sga = new SimpleGrantedAuthority(role);
				roles.add(sga);
			}
			
			//Coverting UserInfo model class into spring based pre-defined User class
			User user = new User(userInfo.getUname(), userInfo.getPassword(), roles);
			*/
			
			
			//USING Lambda Expression + Stream API 
			User user = new User(
					userInfo.getUname(), 
					userInfo.getPassword(), 
					userInfo.getRoles().stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toSet()));
			System.err.println(user.getAuthorities());
			return user;
		}
	}
}
