package com.ps.cff.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ps.cff.entity.ForecastManager;
import com.ps.cff.entity.Status;

@Repository
public interface ForecastManagerRepository extends JpaRepository<ForecastManager, Integer> , JpaSpecificationExecutor<ForecastManager>{


	
	
	/**
	 * 
	 * @param status
	 * @return {@link List}
	 */
	@Query(value = "SELECT * FROM FORECASTE_MANAGER  WHERE STATUS='PUBLIC' OR STATUS=:status",
			nativeQuery = true)
	List<ForecastManager> findByStatus(final String status);
	
	/**
	 * 
	 * @param forecastName
	 * @return {@link Optional}
	 */
	@Query(
			  value = "SELECT * FROM FORECASTE_MANAGER  WHERE FORECAST_NAME = :forecastName", 
			  nativeQuery = true)
	Optional<ForecastManager> findForecastManagerByForecastName(final String forecastName);
	
}
