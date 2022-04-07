package com.ps.cff.response;

import java.util.List;

import lombok.Data;
/**
 * For Error response 
 * @author Hemant 
 *
 */
@Data
public class ErrorResponse {

	private String message;
	private List<String> details;
}
