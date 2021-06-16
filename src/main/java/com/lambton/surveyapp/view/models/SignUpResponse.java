/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 9:41:55 PM
 *
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignUpResponse {
	
	private boolean success;
	
	private String message;

}
