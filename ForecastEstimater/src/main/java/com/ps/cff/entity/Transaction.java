package com.ps.cff.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


import lombok.Data;

/**
 * 
 * @author Hemant
 *
 */
@Data
@Entity
@Table(name="TRANSACTION")
@NamedNativeQuery(
	    name = "Transaction.findByAccountNumberAndTransactionDate",
	    query = "SELECT * FROM TRANSACTION t WHERE t.ACCOUNT_NUMBER=:accountNumber AND TRANSACTION_DATE BETWEEN :startDate AND :endDate ORDER BY t.TRANSACTION_TYPE ASC"
	    ,resultClass=Transaction.class)
public class Transaction implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 
	 * TRANSACTION_ID created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="TRANSACTION_ID")
	private int transactionId;
	
	/*
	 * 
	 * ACCOUNT_NUMBER created
	 */
	@Column(name ="ACCOUNT_NUMBER")
	@NotNull
	private long accountNumber;
	
	/*
	 * 
	 * TRANSACTION_TYPE created
	 */
	@Column(name ="TRANSACTION_TYPE")
	@NotNull
	private String transactionType;
	
	/*
	 * 
	 * DESCRIPTION created
	 */
	@Column(name="DESCRIPTION")
	private String description;
	
	/*
	 * 
	 * AMOUNT created
	 */
	@Column(name ="AMOUNT")
	@NotNull
	private float amount;
	
	/*
	 * 
	 * TOTAL_AMOUNT created
	 */
	@Column(name ="TOTAL_AMOUNT")
	private float totalAmount;

	/*
	 * 
	 * TRANSACTION_DATE created
	 */	
	@Column(name ="TRANSACTION_DATE")
	@NotNull
	private LocalDate transactionDate;
	
}
