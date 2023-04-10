package com.exam.entity;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority{

	 private String authority;
	 
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return this.authority;
	}

	public Authority(String authority) {
		this.authority = authority;
	}

	

}
