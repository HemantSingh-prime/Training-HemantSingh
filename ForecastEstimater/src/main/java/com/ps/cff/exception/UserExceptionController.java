package com.ps.cff.exception;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.ps.cff.response.ErrorResponse;

/**
 * 
 * @author Hemant
 *
 */
@ControllerAdvice
public class UserExceptionController {

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<Object> exception(UserNotFoundException exception) {
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());

		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("User not found exception");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response, HttpStatus.NOT_FOUND);
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(value = UserNotAuthorizeException.class)
	public ResponseEntity<Object> exception(UserNotAuthorizeException exception) {
		List<String> details = new ArrayList<String>();
		details.add(HttpStatus.NOT_FOUND.toString());

		ErrorResponse response = new ErrorResponse();
		if (exception.getMessage() == null)
			response.setMessage("User not authorized to access");
		else
			response.setMessage(exception.getMessage());
		response.setDetails(details);
		return new ResponseEntity<Object>(response, HttpStatus.UNAUTHORIZED);
	}
}
