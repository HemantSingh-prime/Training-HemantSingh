package com.ps.cff.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ps.cff.entity.AccountData;
import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.Roles;
import com.ps.cff.entity.Transaction;
import com.ps.cff.entity.User;
import com.ps.cff.exception.ForecastManagerNotFoundException;
import com.ps.cff.exception.UserNotAuthorizeException;
import com.ps.cff.exception.UserNotFoundException;
import com.ps.cff.service.AccountDataService;
import com.ps.cff.service.ForecastManagerService;
import com.ps.cff.service.RabbitMqSenderService;
import com.ps.cff.service.RolesService;
import com.ps.cff.service.TransactionService;
import com.ps.cff.service.UserService;

@RestController
@RequestMapping("/forecasteController")
public class ForecastController {

	private static final Logger logger = LoggerFactory.getLogger(ForecastController.class);
	/*
	 * 
	 * Creating userService
	 */
	@Autowired
	private UserService userService;

	/*
	 * 
	 * Creating rolesService
	 */
	@Autowired
	private RolesService rolesService;

	/*
	 * 
	 * Creating transactionService
	 */
	@Autowired
	private TransactionService transactionService;

	/*
	 * 
	 * Creating accountDataService
	 */
	@Autowired
	private AccountDataService accountDataService;

	/*
	 * 
	 * Creating forecastManagerService
	 */
	@Autowired
	private ForecastManagerService forecastManagerService;
	/*
	 * 
	 * Creating passwordEncoder
	 */
	@Autowired
	private PasswordEncoder passwordEncoder;

	/*
	 * 
	 * Creating rabbitMqSenderService
	 */
	@Autowired
	private RabbitMqSenderService rabbitMqSenderService;
	@Value("${app.message}")
	private String message;
	/*
	 * 
	 * Creating redisTemplate
	 */
	@Autowired
	private RedisTemplate<String, Object> redisTemplate;
	private Map<String, Object> mapObject;
	private static final String REDIS_USER_CACHE = "forecast";
	static String status = "SUCCESS";

	/**
	 * To fetch all forecast manager based on user roles
	 * 
	 * @param userName
	 * @return {@link ResponseEntity}
	 */

	@GetMapping("/findAllForecasteManagerByStatus/{userName}")
	public ResponseEntity<List<ForecastManager>> findAllForecastManagerByStatus(
			@PathVariable("userName") String userName) {
		List<ForecastManager> listForecastManager;
		mapObject = new HashMap<String, Object>();
		User user;
		Optional<User> optionalUser = userService.findUserByUserName(userName);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException();
		else
			user = optionalUser.get();

		if (user.getRoleId() == 1 || user.getRoleId() == 2)
			listForecastManager = forecastManagerService.fetchAllForecastManagerOnRole(user);
		else
			throw new UserNotAuthorizeException();

		if (listForecastManager.isEmpty()) {
			status = "UNSUCCESS";
			mapObject.put(status, listForecastManager);
			redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);
			throw new UserNotAuthorizeException();
		}

		mapObject.put(status, listForecastManager);
		redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);
		// hashForecastOperation.putIfAbsent(REDIS_USER_CACHE,status,listForecastManager);
				// hashForecastOperation.put(REDIS_USER_CACHE,status,listForecastManager);
		return new ResponseEntity<List<ForecastManager>>(listForecastManager, HttpStatus.OK);
	}

	/**
	 * To generate forecast manager
	 * 
	 * @param userName
	 * @param forecastManager
	 * @return {@link ResponseEntity}
	 */

	@PostMapping("/generateForecasteManager/{userName}")
	public ResponseEntity<ForecastManager> generateForecastManager(@PathVariable("userName") String userName,
			@RequestBody ForecastManager forecastManager) {

		// User user = new User();
		Optional<User> optionalUser = userService.findUserByUserName(userName);
		if (optionalUser.isEmpty())
			throw new UserNotFoundException();
		if (forecastManager == null || forecastManager.getDuration() == null || forecastManager.getStatus() == null)
			throw new ForecastManagerNotFoundException();
		else if (forecastManager.getDuration().equalsIgnoreCase("1W")
				|| forecastManager.getDuration().equalsIgnoreCase("1M")
				|| forecastManager.getDuration().equalsIgnoreCase("3M"))
			// Invoking forecast service with the initial inputs and generate forecast based
			// on given duration
			forecastManager = forecastManagerService.generateForecastManager(forecastManager);

		else
			throw new ForecastManagerNotFoundException("Duration Not Matched Exception");

		// Sending message to exchange
		// rabbitMqSenderService.send(forecastManager);
		// Creating map object and initialize
		if (forecastManager == null)
			status = "UNSUCCESS";

		mapObject = new HashMap<String, Object>();
		mapObject.put(status, forecastManager);
		// Add map object along with request status
		redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);
		logger.info(message);
		return new ResponseEntity<ForecastManager>(forecastManager, HttpStatus.OK);
	}

	/**
	 * To update forecast manager by forecast id
	 * 
	 * @param userName
	 * @param forecastManager
	 * @return {@link ResponseEntity}
	 */
	@PatchMapping("/updateForecastManager/{userName}")
	public ResponseEntity<ForecastManager> updateForecastManager(@PathVariable("userName") String userName,
			@RequestBody ForecastManager forecastManager) {
		User user = null;
		// Checking user role
		Optional<User> optionalUser = userService.findUserByUserName(userName);
		if (!optionalUser.isPresent())
			throw new UserNotFoundException();

		// Invoking forecast manager service to update description and forecastName
		ForecastManager forecastManagerUpdated = forecastManagerService.updateForecatManager(forecastManager);
		if (forecastManagerUpdated == null)
			status = "UNSUCCESS";
		
		mapObject = new HashMap<String, Object>();
		mapObject.put(status, forecastManager);
		// Add map object along with List
		redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);

		return new ResponseEntity<ForecastManager>(forecastManagerUpdated, HttpStatus.OK);
	}

	/**
	 * To remove forecast manager by forecast name
	 * 
	 * @param userName
	 * @param forecastName
	 * @return
	 */
	@DeleteMapping("/deleteForecastManager/{userName}")
	public ResponseEntity<String> deleteForecastManager(@PathVariable("userName") String userName,
			@RequestParam String forecastName) {
		User user;
		// Getting user from table by using user repository
		Optional<User> optionalUser = userService.findUserByUserName(userName);
		if (!optionalUser.isPresent())
			throw new UserNotFoundException();
		else {
			user = optionalUser.get();
		}
		// Invoking forecast manager service to delete forecast
		String response = forecastManagerService.removeForecastManager(user, forecastName);
		
		if (response.isBlank()) {
			status = "UNSUCCESS";
			mapObject.put(status, response);
			// Add map object along with List
			redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);
			throw new UserNotAuthorizeException();
		}
		
		mapObject.put(status, response);
		// Add map object along with List
		redisTemplate.opsForList().leftPushAll(REDIS_USER_CACHE, mapObject);
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	
	
	
	
	
	@PostMapping("/registerUser")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		String response = userService.registerUser(user);
		logger.info(response);
		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@PostMapping("/saveRoles")
	public ResponseEntity<String> saveRoles(@RequestBody Roles roles) {

		String response = rolesService.saveRoles(roles);

		return new ResponseEntity<String>(response, HttpStatus.OK);

	}

	@PostMapping("/startTransaction")
	public ResponseEntity<String> startTransaction(@RequestBody Transaction transaction) {

		String response = transactionService.addTransaction(transaction);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}

	@PostMapping("/openAccount")
	public ResponseEntity<String> openAccount(@RequestBody AccountData accountData) {

		String response = accountDataService.openAccount(accountData);

		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
}
