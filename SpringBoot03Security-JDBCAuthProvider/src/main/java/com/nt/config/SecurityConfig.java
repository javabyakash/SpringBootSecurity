package com.nt.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@SuppressWarnings("deprecation")
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	//1. Autowired Datasource Object
	@Autowired
	private DataSource ds;
	
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		System.out.println(ds.getClass().getName());	//com.zaxxer.hikari.HikariDataSource
		//2. Using jdbcAuthenticationProvider with encrypted password.
		auth.jdbcAuthentication().dataSource(ds)
		.passwordEncoder(new BCryptPasswordEncoder())
		.usersByUsernameQuery("SELECT UNAME,PWD,STATUS FROM USERS WHERE UNAME=?")		//FOR AUTHENTICATION
		.authoritiesByUsernameQuery("SELECT UNAME,ROLE FROM USER_ROLES WHERE UNAME=?");//FOR AUTHORIZATION
		
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/balance").hasAnyAuthority("CUSTOMER","MANAGER")
		.antMatchers("/loan").hasAuthority("MANAGER")
		.antMatchers("/offers").hasAuthority("CUSTOMER")
		.anyRequest().authenticated()
		
		//Basic login having some drawbacks like : logout, remember me and etc...
		//.and().httpBasic()
		
		//Form login supporting form based login with remember me and ...etc features
		.and().formLogin()
		
		//Logout filter with default URL : /logout
		//.and().logout()
				//OR
		//Customized Logout filter with our own URL
		.and().logout().logoutRequestMatcher(new AntPathRequestMatcher("/signout"))
		
		//Remember me filter
		.and().rememberMe()
		
		//Exception Handling for Access Denied Page
		.and().exceptionHandling().accessDeniedPage("/denied")
		
		//Session Management Filter  (Max no. of sessions)
		.and().sessionManagement().maximumSessions(2).maxSessionsPreventsLogin(true);
	}
}
