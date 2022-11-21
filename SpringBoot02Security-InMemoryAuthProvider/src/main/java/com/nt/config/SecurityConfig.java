package com.nt.config;

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
	
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		//auth.inMemoryAuthentication().withUser("raja").password("{noop}rani").authorities("CUSTOMER");
		//auth.inMemoryAuthentication().withUser("akash").password("{noop}java").authorities("MANAGER");
		//auth.inMemoryAuthentication().withUser("akash").password("{noop}mah").roles("CUSTOMER");
		//auth.inMemoryAuthentication().withUser("raja").password("{noop}rani").roles("MANAGER");
		
		//Encrypted password
		auth.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.withUser("raja")
		.password("$2a$10$Mfc8HutTEQVE.Rfvj1MZvOtT4.jk/VZmyilYKw5Houii6FCkLHs2y")
		.authorities("CUSTOMER");
		
		auth.inMemoryAuthentication()
		.passwordEncoder(new BCryptPasswordEncoder())
		.withUser("akash")
		.password("$2a$10$gu.kvVBokUjRiBh7U3y91enZExNgETq2ATBeKUiT16.IrY3wM2j1i")
		.authorities("MANAGER");
	}
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/").permitAll()
		.antMatchers("/balance").hasAnyAuthority("CUSTOMER","MANAGER")
		.antMatchers("/loan").hasAuthority("MANAGER")
		.antMatchers("/offers").authenticated()
					//OR
		//.antMatchers("/balance").hasAnyRole("CUSTOMER","MANAGER")
		//.antMatchers("/loan").hasRole("MANAGER")
		//.antMatchers("/offers").authenticated()
		.anyRequest().authenticated()
		
		//Basic login having some drawbacks like : logout, remember me and etc...
		//.and().httpBasic()
		
		//Form login supporting form based login with remember me and ...etc features
		.and().formLogin()
		
		//Logout filter with default URL : /logout
		//.and().logout()
		
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
