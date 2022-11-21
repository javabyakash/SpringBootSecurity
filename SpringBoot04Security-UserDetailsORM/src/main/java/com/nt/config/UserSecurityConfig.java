package com.nt.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class UserSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/bank/").permitAll()
		.antMatchers("/user/register","/user/showLogin").permitAll()
		.antMatchers("/bank/balance").hasAnyAuthority("User","Manager")
		.antMatchers("/bank/loan").hasAuthority("Manager")
		.antMatchers("/bank/offer").authenticated()
		.anyRequest().authenticated()
		
		//1. form login filter
		.and().formLogin()
		.defaultSuccessUrl("/bank/", true)
		.loginPage("/user/showLogin")
		.loginProcessingUrl("/login")
		.failureUrl("/user/showLogin?error")
		
		//2. add remember me 
		.and().rememberMe()
		
		//3. add logout filter
		.and().logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
		.logoutSuccessUrl("/user/showLogin?logout")
		
		//4. add exception handling filter
		.and().exceptionHandling().accessDeniedPage("/bank/access")
		
		//5. add max session allowed count filter
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true);
		
		//disable CSRF (By default CSRF is enables by SpringBoot)
		//http.csrf().disable();
		
	}
}
