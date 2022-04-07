package com.ps.cff.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.cff.entity.User;
import com.ps.cff.exception.UserNotFoundException;
import com.ps.cff.repository.RolesRepository;
import com.ps.cff.repository.UserRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class UserServiceImpl implements UserService {

	/**
	 * Created userRepository 
	 */
	@Autowired
	private UserRepository userRepository;
	/**
	 * Created rolesRepository 
	 */
	@Autowired
	private RolesRepository rolesRepository;
	
	
	/**
	 * Fetching user based on user name
	 * @param userName
	 */
		@Override
		public Optional<User> findUserByUserName(String userName) {
			   Optional<User> optionalUser=userRepository.findByUserName(userName);
			   
			   
			   
			return optionalUser;
		}
	
	/**
	 * @param user
	 */
	@Override
	public String registerUser(User user) {
		String result="";
		//saving user in DB by using user repository 
		Optional<User> optionalUser=Optional.of(user);
		//using optional to avoid empty user registration 
		if(optionalUser.isPresent()) {
			userRepository.save(user);
			result="User :"+user.getUserName()+" is Register ";
		}
		else {
			result="Empty User Cannot be register ";
		}
		return result;
	}

	@Override
	public User fetchUser(String userName, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> fetchAllUser() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
