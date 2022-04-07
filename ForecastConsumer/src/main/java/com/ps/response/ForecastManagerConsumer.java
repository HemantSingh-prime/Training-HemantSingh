package com.ps.response;

import java.time.LocalDate;
import java.util.List;

import com.ps.response.Status;
import com.ps.response.Transaction;

import lombok.Data;
@Data
public class ForecastManagerConsumer {

	private int forecastId;
	private String forecastName;
	private long accountNumber;
	private String description;
	private List<Transaction> listTransaction;
	private Status status;
	private String duration;
	private LocalDate startDate;
	private LocalDate endDate;
}
