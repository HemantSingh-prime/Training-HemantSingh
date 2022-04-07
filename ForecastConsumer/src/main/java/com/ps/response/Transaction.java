package com.ps.response;

import java.time.LocalDate;

import lombok.Data;
@Data
public class Transaction {

	private int transactionId;
	private long accountNumber;
	private String transactionType;
	private String description;
	private float amount;
	private float totalAmount;
	private LocalDate transactionDate;
}
