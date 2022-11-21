package com.nt.model;

import java.util.List;
import java.util.Set;

import javax.annotation.Generated;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER_INFO")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class UserInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer uid;
	@Column(name = "name", nullable = false)
	private String uname;
	@Column(name = "password", length = 240, nullable = false)
	private String password;
	@Column(name = "email", unique = true, nullable = false)
	private String email;
	@Column(name = "user_status")
	private Boolean status=true;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
			name = "ROLES_TAB",
			joinColumns = @JoinColumn(name = "usr_id")
			)
	@Column(name = "role")
	private Set<String> roles;
}
