package com.ps.cff.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Hemant
 *
 */
@Data
@Entity
@Table(name="FORECASTE_MANAGER")
@AllArgsConstructor
@NoArgsConstructor
//@NamedQuery(
//	    name = "ForecastManager.findByStatus",
//	    query = "SELECT * FROM FORECASTE_MANAGER f WHERE f.STATUS=:status")
public class ForecastManager implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/*
	 * 
	 * FORECAST_ID created
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="FORECAST_ID")
	private int forecastId;
	
	/*
	 * 
	 * FORECAST_NAME created
	 */
	@Column(name="FORECAST_NAME",unique = true)
	@NotNull
	private String forecastName;
	
	/*
	 * 
	 * ACCOUNT_NUMBER created
	 */
	@Column(name ="ACCOUNT_NUMBER")
	@NotNull
	private long accountNumber;
	
	/*
	 * 
	 * DESCRIPTION created
	 */
	@Column(name ="DESCRIPTION")
	private String description;
	
	/*
	 * 
	 * Transaction list created
	 */
	@Column(name ="LIST_TRANSACTION")
	@ManyToMany
	private List<Transaction> listTransaction;
	

	/*
	 * 
	 * STATUS created
	 */
	@Column(name ="STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	/*
	 * 
	 * DURATION created
	 */
	@Column(name="DURATION")
	@NotNull
	private String duration;
	/*
	 * 
	 * STATUS_DATE created
	 */
	@Column(name ="START_DATE")
	@NotNull
	private LocalDate startDate;
	
	/*
	 * 
	 * END_DATE created
	 */
	@Column(name ="END_DATE")
	@NotNull
	private LocalDate endDate;
}
