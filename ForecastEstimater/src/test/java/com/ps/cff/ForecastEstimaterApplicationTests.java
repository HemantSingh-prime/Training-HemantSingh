package com.ps.cff;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ActiveProfiles;


import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.Status;
import com.ps.cff.entity.Transaction;
import com.ps.cff.entity.User;
import com.ps.cff.exception.ForecastManagerNotFoundException;
import com.ps.cff.repository.ForecastManagerRepository;
import com.ps.cff.repository.TransactionRepository;
import com.ps.cff.repository.UserRepository;
import com.ps.cff.service.ForecastManagerService;
import com.ps.cff.service.ForecastManagerServiceImpl;



@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
@TestInstance(Lifecycle.PER_CLASS)
class ForecastEstimaterApplicationTests {

	private Logger logger=LogManager.getLogger(ForecastEstimaterApplicationTests.class);
	
	@Mock
	private ForecastManagerRepository forecastManagerRepository;
	@Mock
	private TransactionRepository transactionRepository;
	@Mock
	private UserRepository userRepository;
	@InjectMocks
	private ForecastManagerServiceImpl forecastManagerService;
	
	@SuppressWarnings("deprecation")
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	List<ForecastManager> listForecastManager,listForecastManager1;
	Transaction transaction;
	Transaction transaction1;
	List<Transaction> listTrasnsaction;
	ForecastManager forecastManager1;
	ForecastManager forecastManager2;
	private static  String status;
	
	private  User user; 
	private User user1;
	
	
	
	/**
	 * Initialize with test value
	 */
	@BeforeAll()
	void initUseCase() {
		listForecastManager=new ArrayList<ForecastManager>();
		listForecastManager1=new ArrayList<ForecastManager>();
		transaction=new Transaction();
		transaction1=new Transaction();
		listTrasnsaction=new ArrayList<Transaction>();
		forecastManager1=new ForecastManager();
		forecastManager2=new ForecastManager();
		
		user=new User();
		user1=new User();
		user.setId(3);user.setUserName("ravi@jkl.com");user.setPassword("abc@123");user.setRoleId(1);
		user1.setId(2);user1.setUserName("dfg@gmn.com");user1.setRoleId(2);user1.setPassword("gdhd@123");
		
		//Setting default data in Forecast manager List
		transaction.setAccountNumber(2012010);transaction.setAmount(1000.0f);transaction.setTransactionType("DR");transaction.setTransactionDate(LocalDate.now());
		transaction1.setAccountNumber(2012010);transaction1.setAmount(10000.0f);transaction1.setTransactionType("CR");transaction1.setTransactionDate(LocalDate.now());
		listTrasnsaction.add(transaction);listTrasnsaction.add(transaction1);
		forecastManager1.setForecastId(1);forecastManager1.setAccountNumber(2012010);forecastManager1.setDuration("1M");forecastManager1.setStartDate(LocalDate.now());
		forecastManager1.setForecastName("cart_2");forecastManager1.setStatus(Status.PUBLIC);forecastManager1.setListTransaction(listTrasnsaction);forecastManager1.setEndDate(LocalDate.now().plusMonths(1));
		forecastManager2.setForecastId(2);forecastManager2.setAccountNumber(2012010);forecastManager2.setDuration("1W");forecastManager2.setStartDate(LocalDate.now());
		forecastManager2.setForecastName("cart_3");forecastManager2.setStatus(Status.PRIVATE);forecastManager2.setListTransaction(listTrasnsaction);forecastManager2.setEndDate(LocalDate.now().plusDays(7));
		listForecastManager.add(forecastManager1);listForecastManager.add(forecastManager2);
		listForecastManager1.add(forecastManager1);
	}

	  /**
	   * Admin can have access of all data like public or private and find by status
	   */
	@Test
	void findByStatus_successUserAdmin() {
		status="PRIVATE";
		Mockito.when(forecastManagerRepository.findByStatus(status)).thenReturn(listForecastManager);
		//List<ForecastManager> listForecastManager=forecastManagerRepository.findByStatus(status);
		List<ForecastManager> list=forecastManagerService.fetchAllForecastManagerOnRole(user);
		
	    assertThat(list.size()).isGreaterThanOrEqualTo(1);
		//assertEquals(Status.PRIVATE,list.get(1).getStatus());
		logger.info("findByStatus_success excuted");
	}
	
	
	/**
	 * Admin can have access of all data access and find by status method need private as input
	 */
	@Test
	void findByStatus_unSuccessUserAdmin() {
    	status="PUBLIC";
		Mockito.when(forecastManagerRepository.findByStatus(status)).thenReturn(listForecastManager);
		//System.out.println(listForecastManager);
		List<ForecastManager> list=forecastManagerService.fetchAllForecastManagerOnRole(user);
		assertThat(list.size()).isEqualTo(0);
		
	}
	
	
	/**
	 * Operator can have only access of public data and find by status method take public as input
	 */
    @Test
	void findByStatus_successUserOperator() {
   	    status="PUBLIC";
		Mockito.when(forecastManagerRepository.findByStatus(status)).thenReturn(listForecastManager1);
		List<ForecastManager> list=forecastManagerService.fetchAllForecastManagerOnRole(user1);
		assertThat(list.size()).isGreaterThanOrEqualTo(1);
		
	}
    
	
    /**
     * Operator can have only access of public data and find by status method take public as input
     */
     @Test
	void findByStatus_unSuccessUserOperator() {
    	 status="PRIVATE";
		Mockito.when(forecastManagerRepository.findByStatus(status)).thenReturn(listForecastManager1);
		List<ForecastManager> list=forecastManagerService.fetchAllForecastManagerOnRole(user1);
		assertThat(list.size()).isEqualTo(0);
		
	}
     
     
     /**
      *Test generate forecast manager 
      */
     
     @Test
     void generateForecastManager_success() {
    	 
    	 Mockito.when(forecastManagerRepository.save(forecastManager1)).thenReturn(forecastManager1);
    	 
    	 ForecastManager forecastManager=forecastManagerService.generateForecastManager(forecastManager1);
    	 
    	 assertEquals(forecastManager1.getDuration(),forecastManager.getDuration());
     }
     /**
      * Test for remove forecast from table
      * @throws Exception
      */
     @Test
     void removeForecastManager_success()throws Exception {
    	 
    	 Optional<User> optionalUser=Optional.of(user);
    	 Optional<ForecastManager> optionalForecast=Optional.of(forecastManager1);
    	 Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(optionalUser);
    	 Mockito.when(forecastManagerRepository.findForecastManagerByForecastName(forecastManager1.getForecastName())).thenReturn(optionalForecast);
    	 
    	 
          forecastManagerService.removeForecastManager(user,forecastManager1.getForecastName());             
    	 verify(forecastManagerRepository,Mockito.times(1)).deleteById(forecastManager1.getForecastId());
                       
     }
     /**
      * Test for update forecast from table
      */
     @Test
     void updateForecatManager_success() {
    	 
    	 Optional<User> optionalUser=Optional.of(user);
    	 Optional<ForecastManager> optionalForecast=Optional.of(forecastManager1);
    	 Mockito.when(userRepository.findByUserName(user.getUserName())).thenReturn(optionalUser);
    	 Mockito.when(forecastManagerRepository.findById(forecastManager1.getForecastId())).thenReturn(optionalForecast);
    	 forecastManager1.setForecastName("Nem_Cart_3");
    	 forecastManager1.setDescription("Forecast Description updated ");
    	 Mockito.when(forecastManagerRepository.save(forecastManager1)).thenReturn(forecastManager1);
    	 ForecastManager forecastManagerUpdated=forecastManagerService.updateForecatManager( forecastManager1);
    	 assertNotNull(forecastManagerUpdated);
    	 assertEquals(forecastManager1.getForecastName(),forecastManagerUpdated.getForecastName());
    	 assertEquals(forecastManager1.getDescription(),forecastManagerUpdated.getDescription());
    	 
     }
     
}
