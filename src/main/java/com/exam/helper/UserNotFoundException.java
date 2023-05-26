package com.exam.helper;

public class UserNotFoundException extends Exception{
	public UserNotFoundException() {
		super("User not found in the Database !!");
	}
	public UserNotFoundException(String str) {
		super(str);
	}
}
