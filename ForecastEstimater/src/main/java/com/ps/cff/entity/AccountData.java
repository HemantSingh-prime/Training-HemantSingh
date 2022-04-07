package com.ps.cff.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name="ACCOUNT_DATA")
public class AccountData implements Serializable{

	/*
	 * 
	 * account number created
	 */
	@Id
	@Column(name="ACCOUNT_NUMBER")
	@NotNull
	private long accountNumber;
	
	/*
	 * 
	 * CUSTOMER_NAME created
	 */
	@Column(name="CUSTOMER_NAME")
	@NotNull
	private String customerName;
	
	/*
	 * 
	 * ACCOUNT_TYPE created
	 */
	@Column(name="ACCOUNT_TYPE")
	@NotNull
	private String accountType;
	
	/*
	 * 
	 * ADDRESS created
	 */
	@Column(name="ADDRESS")
	@NotNull
	private String address;
	
	/*
	 * 
	 * BALANCE created
	 */
	@Column(name="BALANCE")
	private float  balance;
	
	/*
	 * 
	 * Transaction collection created
	 */
	@OneToMany
	@JoinColumn(name = "ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER")
	private Set<Transaction> transaction;
	
	/*
	 * 
	 * Transaction collection created
	 */
	@OneToMany
	@JoinColumn(name = "ACCOUNT_NUMBER", referencedColumnName = "ACCOUNT_NUMBER")
	private Set<ForecastManager> forecastManager;
	
}
