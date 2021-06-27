/**
 * &copyright upski international
 */
package com.lambton.surveyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambton.surveyapp.service.AuthService;
import com.lambton.surveyapp.view.models.AuthRequest;
import com.lambton.surveyapp.view.models.AuthResponse;
import com.lambton.surveyapp.view.models.SignUpRequest;
import com.lambton.surveyapp.view.models.SignUpResponse;

/**
 * @author Jijo Raju
 * @Since Jun 12, 2021 9:33:07 PM
 *
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> authenticate(@RequestBody AuthRequest request) {
		try {
			AuthResponse authRes = authService.authenticate(request);
			return ResponseEntity.ok()
					.header("Set-Cookie", "JSESSIONID=" + authRes.getToken() + "; Max-Age=604800; HttpOnly;")
					.body(authRes);
		}
		catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest signupRequest) {
		SignUpResponse signupResponse = authService.signup(signupRequest);
		if (signupResponse.isSuccess()) {
			return ResponseEntity.status(HttpStatus.OK).body(signupResponse);
		}
		else {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(signupResponse);
		}
	}
}
