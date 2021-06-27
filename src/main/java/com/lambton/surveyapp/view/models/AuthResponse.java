/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Jijo Raju
 * @Since Jun 12, 2021 9:40:28 PM
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
	
	private String username;
	private String token;
	private String role;

}
