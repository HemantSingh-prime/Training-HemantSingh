package com.ps.cff.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ps.cff.entity.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	/**
	 * 
	 * @param accountNumber
	 * @param startDate
	 * @param endDate
	 * @return {@link List}
	 */
   List<Transaction>	findByAccountNumberAndTransactionDate(long accountNumber,LocalDate startDate,LocalDate endDate);

}
