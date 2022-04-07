package com.ps.cff.service;

import static org.junit.Assume.assumeTrue;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import javax.annotation.Resource;
import javax.xml.bind.ParseConversionEvent;

import org.aspectj.weaver.ast.Or;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.Status;
import com.ps.cff.entity.Transaction;
import com.ps.cff.entity.User;
import com.ps.cff.exception.ForecastManagerNotFoundException;
import com.ps.cff.exception.UserNotAuthorizeException;
import com.ps.cff.exception.UserNotFoundException;
import com.ps.cff.repository.ForecastManagerRepository;
import com.ps.cff.repository.RolesRepository;
import com.ps.cff.repository.TransactionRepository;
import com.ps.cff.repository.UserRepository;
import com.ps.cff.specification.ForecastManagerSpecification;
import com.ps.cff.specification.SearchCriteria;

/**
 * 
 * @author Hemant
 *
 */

@Service
public class ForecastManagerServiceImpl implements ForecastManagerService {

	private static final Logger logger = LoggerFactory.getLogger(ForecastManagerServiceImpl.class);
	/*
	 * 
	 * Creating forcastManagerRepository
	 */
	@Autowired
	private ForecastManagerRepository forcastManagerRepository;

	/*
	 * 
	 * Creating transactionRepository
	 */
	@Autowired
	private TransactionRepository transactionRepository;

	/**
	 * Fetching record from forecast manager table using repository
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public List<ForecastManager> fetchAllForecastManagerOnRole(User user) {

		List<ForecastManager> listForecastManager;

		SearchCriteria serachCriteria1 = new SearchCriteria("status", ":", Status.PRIVATE);
		SearchCriteria serachCriteria2 = new SearchCriteria("status", ":", Status.PUBLIC);
		ForecastManagerSpecification privateSpecification = new ForecastManagerSpecification(serachCriteria1);
		ForecastManagerSpecification publicSpecification = new ForecastManagerSpecification(serachCriteria2);

		listForecastManager = forcastManagerRepository
				.findAll(user.getRoleId() == 1 ? Specification.where(privateSpecification).or(publicSpecification)
						: publicSpecification);

//		if (user.getRoleId() == 1)
//			listForecastManager = forcastManagerRepository.findByStatus(status1);
//
//		else
//			listForecastManager = forcastManagerRepository.findByStatus(status2);

		return listForecastManager;
	}

	/**
	 * For generate forecast manager and update in data base
	 * 
	 * @param forecastManger
	 * @return
	 */

	@Override
	@Transactional(rollbackFor = { Exception.class })
	public ForecastManager generateForecastManager(ForecastManager forecastManager) {
		List<Transaction> listTransaction;

		if (forecastManager.getDuration().equalsIgnoreCase("1W")) {
			forecastManager.setStartDate(LocalDate.now());
			forecastManager.setEndDate(LocalDate.now().plusDays(7));
		} else if (forecastManager.getDuration().equalsIgnoreCase("1M")) {
			forecastManager.setStartDate(LocalDate.now());
			forecastManager.setEndDate(LocalDate.now().plusMonths(1));
		} else if (forecastManager.getDuration().equalsIgnoreCase("3M")) {
			forecastManager.setStartDate(LocalDate.now());
			forecastManager.setEndDate(LocalDate.now().plusMonths(3));
		}
		// Invoking transaction from transaction repository
		listTransaction = transactionRepository.findByAccountNumberAndTransactionDate(
				forecastManager.getAccountNumber(), forecastManager.getStartDate(), forecastManager.getEndDate());

		System.out.println(listTransaction);
		forecastManager.setListTransaction(listTransaction);

		// Insert generated forecast in data base

		forcastManagerRepository.save(forecastManager);

		if (forecastManager.getListTransaction() == null)
			throw new ForecastManagerNotFoundException("Forecast not generated exception");

		return forecastManager;
	}

	/**
	 * For delete forecast manager in table
	 * 
	 * @param forecastName
	 * @param userName
	 * @return
	 */

	@Override
	public String removeForecastManager(User user, final String forecastName) {
		ForecastManager forecastManager = new ForecastManager();

		String result = "";

		// Load forecast manager by forecast name using forecast repository
		Optional<ForecastManager> optionalForecastManager = forcastManagerRepository
				.findForecastManagerByForecastName(forecastName);

		if (optionalForecastManager.isEmpty())
			throw new ForecastManagerNotFoundException();

		forecastManager = optionalForecastManager.get();
		if (forecastManager.getStatus() == Status.PRIVATE)
			throw new UserNotAuthorizeException();

		else if (user.getRoleId() == 1) {
			// Deleting forecast manager by id using forecast repository
			forcastManagerRepository.deleteById(forecastManager.getForecastId());
			result = " Forecast manager deleted succesfully";
		}

		return result;
	}

	/**
	 * Update forecast manager in data base
	 * 
	 * @param forecastManager
	 * @param userName
	 * @return
	 */
	@Override
	public ForecastManager updateForecatManager(ForecastManager forecastManager) {

		String result = "";
		ForecastManager forecastManagerdb = new ForecastManager();

		// Find forecast by forecast Id
		Optional<ForecastManager> optionalForecaste = forcastManagerRepository
				.findById(forecastManager.getForecastId());
		if (!optionalForecaste.isPresent())
			throw new ForecastManagerNotFoundException();
		// Convert optional to forecast manager
		forecastManagerdb = optionalForecaste.get();
		if (forecastManager.getDescription() != null && !forecastManager.getDescription().isBlank()) {
			forecastManagerdb.setDescription(forecastManager.getDescription());
			result += "Description ";
		}
		if (forecastManager.getForecastName() != null && !forecastManager.getForecastName().isBlank()) {
			forecastManagerdb.setForecastName(forecastManager.getForecastName());
			result += "forecast name updated";
		}
		// Update description and forecast name in table
		ForecastManager forecastManagerUpdate = forcastManagerRepository.save(forecastManagerdb);

		return forecastManagerUpdate;
	}

}
