package com.exam.helper;

public class UserFoundException extends Exception{
	public UserFoundException() {
		super("User with this username is already in the Database !! Try with another one.");
	}
	public UserFoundException(String str) {
		super(str);
	}
}