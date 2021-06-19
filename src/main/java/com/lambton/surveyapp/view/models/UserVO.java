/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jun 16, 2021 9:03:02 PM
 *
 */

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class UserVO {
	
	private String uniqueId;

	private String email;

	private String username;

	private String firstname;

	private String lastname;
		
	private String address;
	
	private String phoneNumber;

}
