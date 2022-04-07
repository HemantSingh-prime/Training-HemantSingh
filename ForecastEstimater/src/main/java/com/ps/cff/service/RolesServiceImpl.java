package com.ps.cff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate.Param;
import com.ps.cff.entity.Roles;
import com.ps.cff.repository.RolesRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class RolesServiceImpl implements RolesService {

	/**
	 * Created rolesRepository
	 */
	@Autowired
	private RolesRepository rolesRepository;
	
	/**
	 * @param roles
	 *  
	 */
	@Override
	public String saveRoles(Roles roles) {
		String result="";
		//saving roles in DB by using roles repository 
		Optional<Roles> optionalUser=Optional.of(roles);
		//using optional to avoid empty roles  
		if(optionalUser.isPresent()) {
			rolesRepository.save(roles);
			result="User Roles is Register ";
		}
		else {
			result="Empty Roles cannot be register ";
		}
		return result;
	}

}
