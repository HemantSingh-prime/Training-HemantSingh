package com.ps.cff.service;

import java.util.List;
import java.util.Optional;

import com.ps.cff.entity.User;

public interface UserService {

	//Method is for registering user  
	public String registerUser(User user);
	
	//Method for fetching user based on name password
	public User fetchUser(String userName,String password);
	
	//Method for fetching all user 
	public List<User> fetchAllUser();
	
	public Optional<User> findUserByUserName(String userName);
	
	
}
