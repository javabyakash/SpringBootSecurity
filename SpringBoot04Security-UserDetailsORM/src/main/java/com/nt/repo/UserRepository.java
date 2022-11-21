package com.nt.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nt.model.UserInfo;

public interface UserRepository extends JpaRepository<UserInfo, Integer> {
	public Optional<UserInfo> findByEmail(String email);
}
