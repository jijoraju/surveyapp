/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jul 16, 2021 4:21:26 PM
 *
 */
@Getter
@Setter
public class SurveyResponseVO {

	private String userId;
	
	private UserVO user;

	private String surveyId;
	
	private SurveyVO survey;

	private List<QuestionResponseVO> responses;

}
