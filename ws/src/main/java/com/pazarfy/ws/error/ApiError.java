package com.pazarfy.ws.error;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonView;
import com.pazarfy.ws.shared.View;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
	
	@JsonView(View.ResponseView.class)
	private int status;
	
	@JsonView(View.ResponseView.class)
	private String message;
	
	@JsonView(View.ResponseView.class)
	private String path;
	
	@JsonView(View.ResponseView.class)
	private Long timestamp = new Date().getTime();
	
	private Map<String,String> validationerrors;

	
	public ApiError(int status, String message, String path) {
		this.status = status;
		this.message = message;
		this.path = path;
	}
	
	
	

}
