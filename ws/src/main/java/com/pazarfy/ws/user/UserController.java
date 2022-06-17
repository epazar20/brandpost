package com.pazarfy.ws.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pazarfy.ws.dto.UserDto;
import com.pazarfy.ws.dto.UserUpdateDto;
import com.pazarfy.ws.error.ApiError;
import com.pazarfy.ws.shared.CurrentUser;
import com.pazarfy.ws.shared.GenericResponse;
import com.pazarfy.ws.shared.View;

@RestController
@RequestMapping("api/1.0")
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	IUserService service;

	@PostMapping("/users")
	// @ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> CreateUser(@Valid @RequestBody Users user) {
//		if(user.getUsername() == null || user.getUsername().isBlank())
//		{
//			
//			ApiError error = new ApiError(400,"Validation Error","api/1.0/users");
//			Map<String,String> map = new HashMap();
//			map.put("username", "username not null");
//			error.setValidationerrors(map);
//			//return  ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//			return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
//		}

		service.CreateUser(user);
		logger.info(user.toString());
		return ResponseEntity.ok(new GenericResponse("User created"));

	}

	@GetMapping(path = "/users")
	// @JsonView(View.ResponseAuth.class)
	public Page<UserDto> getUsers(Pageable page, @CurrentUser Users auth) {
		return service.getUsers(page, auth).map(user -> {
			return new UserDto(user);
		});
		// return service.getUsers().map(UserDto::new);
	}
	
	
	@GetMapping(path = "/users/{username}")
	public UserDto getUser(@PathVariable String username)
	{
		Users user = service.findByUsername(username);
		return new UserDto(user);
	}
	
	
	
	@PutMapping(path = "users/{username}")
	//@PreAuthorize("#loggedUser != null && #username == #loggedUser.username")
	@PreAuthorize("!isAnonymous() && #username == principal.username")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateDto data,
			@PathVariable String username /* ,@CurrentUser Users loggedUser */)
	{
//		if(username != loggedUser.getUsername())
//		{
//			ApiError error = new ApiError(403, "You cannot edit", "/api/1.0/users"+ username);
//			return ResponseEntity.status(403).body(error);
//		}
		
		Users user = service.updateByUser(username, data);
		return ResponseEntity.ok(new UserDto(user));
	}

//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public ApiError handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
//	{
//		
//		ApiError error = new ApiError(400,"Validation Error","api/1.0/users");
//		Map<String,String> map = new HashMap();
//		
//		for(FieldError er : ex.getBindingResult().getFieldErrors())
//		{
//			map.put(er.getField(), er.getDefaultMessage());
//		}
//		error.setValidationerrors(map);
//		return error;
//		
//	}

}
