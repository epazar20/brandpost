package com.pazarfy.ws.user;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonView;
import com.pazarfy.ws.shared.View;

import lombok.Data;

@Data
@Entity
public class Users implements UserDetails {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull(message = "{pazarfy.username.validation.constraints.NotNull.message}")
	@Size(min=4,max=255,message = "{pazarfy.username.validation.constraints.Size.message}")
	@UniqueUsername(message = "{pazarfy.username.validation.constraints.UniqueUsername.message}")
	//@Column(unique = true)
	@JsonView(View.ResponseAuth.class)
	private String username;

	@NotNull(message = "{pazarfy.displayname.validation.constraints.NotNull.message}")
	@Size(min=4,max=255,message = "{pazarfy.displayname.validation.constraints.Size.message}")
	@JsonView(View.ResponseAuth.class)
	private String displayname;
	
	@NotNull(message = "{pazarfy.password.validation.constraints.NotNull.message}")
	@Size(min=8,max=255,message = "{pazarfy.password.validation.constraints.Size.message}")
	@Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$", message = "{pazarfy.password.validation.constraints.Pattern.message}")
	private String password;
	
	@Lob
	@JsonView(View.ResponseAuth.class)
	private String image;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList("Role_user");
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
