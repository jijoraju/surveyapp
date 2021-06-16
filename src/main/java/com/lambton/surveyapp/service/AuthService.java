/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service;

import com.lambton.surveyapp.view.models.AuthRequest;
import com.lambton.surveyapp.view.models.AuthResponse;
import com.lambton.surveyapp.view.models.SignUpRequest;
import com.lambton.surveyapp.view.models.SignUpResponse;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 9:12:34 PM
 *
 */
public interface AuthService {
	
	public SignUpResponse signup(SignUpRequest signUpRequest);
	
	public AuthResponse authenticate(AuthRequest authRequest);

}
