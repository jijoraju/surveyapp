/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl.helper;

import java.util.UUID;

import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.view.models.SignUpRequest;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 9:53:14 PM
 *
 */
public interface AuthServiceHelper {

	/**
	 * @param signUpRequest
	 * @return
	 */
	static User getUserFromSignUpRequest(SignUpRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setUsername(signUpRequest.getEmail());
		user.setUniqueId(UUID.randomUUID().toString());
		user.setFirstName(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setAccountNonExpired(true);
		user.setAccountNonLocked(true);
		user.setCredentialsNonExpired(true);
		user.setEnabled(true);
		return user;
	}

}
