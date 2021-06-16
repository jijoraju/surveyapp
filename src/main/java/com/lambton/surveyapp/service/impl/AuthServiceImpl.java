/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.lambton.surveyapp.config.TokenUtil;
import com.lambton.surveyapp.db.entities.Role;
import com.lambton.surveyapp.db.entities.RoleType;
import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.repository.RoleRepository;
import com.lambton.surveyapp.db.repository.UserRepository;
import com.lambton.surveyapp.service.AuthService;
import com.lambton.surveyapp.service.impl.helper.AuthServiceHelper;
import com.lambton.surveyapp.view.models.AuthRequest;
import com.lambton.surveyapp.view.models.AuthResponse;
import com.lambton.surveyapp.view.models.SignUpRequest;
import com.lambton.surveyapp.view.models.SignUpResponse;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 9:16:06 PM
 *
 */

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenUtil tokenUtil;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public SignUpResponse signup(SignUpRequest signUpRequest) {
		
		if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new SignUpResponse(false, "Email Address already in use!");
        }
		User user = AuthServiceHelper.getUserFromSignUpRequest(signUpRequest);
		user.setPassword(encoder.encode(signUpRequest.getPassword()));
		Role role = roleRepository.findByType(RoleType.ROLE_USER);
		user.setRoles(Collections.singleton(role));
		userRepository.save(user);
		return new SignUpResponse(true, "Created Successfully");
	}

	@Override
	public AuthResponse authenticate(AuthRequest authRequest) {
		
		Authentication auth = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
		User user = (User) auth.getPrincipal();
		return new AuthResponse(user.getUsername(),
				tokenUtil.createAuthorizationToken(user.getUniqueId(), user.getUsername()));
	}

}
