package com.ps.cff.exception;
/**
 * 
 * @author Hemant
 *
 */
public class ForecastManagerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
   
	public ForecastManagerNotFoundException() {
		super();
	}
	
	public ForecastManagerNotFoundException(String msg) {
		super(msg);
	}
}
