package com.pazarfy.ws.auth;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.pazarfy.ws.shared.View;


@RestController
public class AuthController {

	private final Logger log = LoggerFactory.getLogger(AuthController.class);

	AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}

	@PostMapping(path = "api/1.0/auth")
	AuthResponse handleAuthentication(@RequestBody AuthCrediential cred) throws AuthenticationException, Exception {
		return authService.authenticate(cred);
	}

	// @Autowired
	// IUserService service;

	// @PostMapping(path = "api/1.0/auth")
	// @JsonView(View.ResponseAuth.class)
	// ResponseEntity<?> handleAuthentication((@CurrentUser Users detail) {

	// // Users detail = (Users)
	// // SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	// // username displayname image with JSONView.ResponseAuth
	// return ResponseEntity.status(HttpStatus.OK).body(detail);
	// }

	// @PostMapping(path = "api/1.0/auth")
	// @JsonView(View.ResponseAuth.class)
	// ResponseEntity<?> handleAuthentication(@RequestHeader(name = "Authorization",
	// required = false) String auth) {
	// ApiError error = new ApiError(401, "Unauthorized Request", "api/1.0/auth");
	// if (auth == null) {
	//
	// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	// }
	// log.info(auth);
	// String base = auth.split("Basic ")[1];
	// log.info(base);
	// String deger = new String(Base64.getDecoder().decode(base));//AACAR:NP321
	// log.info(deger);
	//
	// String[] creds = deger.split(":");
	// String username = creds[0];
	// String password = creds[1];
	// log.info(password);
	//
	// Users inDB = service.findByUsername(username);
	// if(inDB == null)
	// {
	// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	// }
	// String hashedPasword = inDB.getPassword();
	// if(!new BCryptPasswordEncoder().matches(password, hashedPasword))
	// {
	// return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
	// }
	//
	// //username displayname image with JSONView.ResponseAuth
	// return ResponseEntity.status(HttpStatus.OK).body(inDB);
	// }

}
