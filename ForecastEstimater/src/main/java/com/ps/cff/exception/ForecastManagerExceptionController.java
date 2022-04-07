package com.ps.cff.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.ps.cff.response.ErrorResponse;

/**
 * 
 * @author Hemant
 *
 */
@ControllerAdvice
public class ForecastManagerExceptionController {

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = ForecastManagerNotFoundException.class)
	public ResponseEntity<Object> exception(ForecastManagerNotFoundException exception) {
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());

		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("Forecast manager not found exception");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

}
