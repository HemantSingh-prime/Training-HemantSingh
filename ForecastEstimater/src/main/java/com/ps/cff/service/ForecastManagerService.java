package com.ps.cff.service;

import java.util.List;

import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.User;


public interface ForecastManagerService {

	public List<ForecastManager> fetchAllForecastManagerOnRole(User user) ;
	
	public ForecastManager  generateForecastManager(ForecastManager forecastManager);
	
	public String removeForecastManager(User user,String forecastName);
	
	public ForecastManager updateForecatManager(ForecastManager forecastManager);
	
	
	

 }
