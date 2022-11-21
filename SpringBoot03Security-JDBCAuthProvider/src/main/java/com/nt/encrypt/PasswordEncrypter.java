package com.nt.encrypt;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncrypter {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String pwd1 = encoder.encode("rani");
		String pwd2 = encoder.encode("hiwale");
		
		System.out.println(pwd1);
		System.out.println(pwd2);
	}

}
