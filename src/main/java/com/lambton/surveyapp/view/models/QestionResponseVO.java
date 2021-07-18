/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jul 16, 2021 4:23:17 PM
 *
 */

@Getter
@Setter
public class QestionResponseVO {
	
	private String answerType;
	private String questionId;
	private String singleAnswer;
	private List<String> multipleAnswer;

}
