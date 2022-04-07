package com.ps.cff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.cff.entity.AccountData;
import com.ps.cff.repository.AccountDataRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class AccountDataServiceImpl implements AccountDataService {

	@Autowired
	private AccountDataRepository accountDataRepository;

	/**
	 * @param accountData
	 * @return 
	 */
	
	@Override
	public String openAccount(AccountData accountData) {
		String result="";
	    Optional<AccountData> optionalAccount=Optional.of(accountData);
	    if(optionalAccount.isPresent()) {
	    	accountDataRepository.save(accountData);
	    	result="Account open in DB";
	    }
	    else
	    	result="Account not open in DB";
	    
		return result;
	}

}
