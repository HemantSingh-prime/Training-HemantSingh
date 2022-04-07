package com.ps.cff.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ps.cff.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	/**
	 * 
	 * @param userName
	 * @return {@link Optional}
	 */
	@Query(value="SELECT * FROM USER  WHERE USER_NAME=?1",nativeQuery = true)
	 Optional<User> findByUserName(String userName);
	
	
}
