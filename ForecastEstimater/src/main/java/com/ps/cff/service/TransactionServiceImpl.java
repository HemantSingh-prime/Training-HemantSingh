package com.ps.cff.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ps.cff.entity.Transaction;
import com.ps.cff.repository.TransactionRepository;
/**
 * 
 * @author Hemant
 *
 */
@Service
public class TransactionServiceImpl implements TransactionService {

	/**
	 * Created transactionRepository
	 */
	@Autowired
	private TransactionRepository transactionRepository;
	
	/**
	 * @param transaction
	 */
	@Override
	public String addTransaction(Transaction transaction) {
		    String result="";
		    Optional<Transaction> optionalTransaction=Optional.of(transaction);
		    if(optionalTransaction.isPresent()) {
		    	transactionRepository.save(transaction);
		    	result="Transaction save in DB";
		    }
		    else
		    	result="Transaction not saved in DB";
		    
		return result;
	}

	

}
