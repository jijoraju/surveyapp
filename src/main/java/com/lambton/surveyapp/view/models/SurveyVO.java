/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 11:01:58 AM
 *
 */

@Getter 
@Setter
public class SurveyVO {
	
	private String uniqueId;
	
	private String name;
	
	private String description;
	
	private String expiryDate;
	
	private String tags;
	
	List<QuestionVO> items;

}
