/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.lambton.surveyapp.db.entities.QuestionResponse;
import com.lambton.surveyapp.db.entities.SurveyResponse;
import com.lambton.surveyapp.view.models.QuestionResponseVO;
import com.lambton.surveyapp.view.models.SurveyResponseVO;

/**
 * @author Jijo Raju
 * @Since Jul 18, 2021 8:52:30 PM
 *
 */
public interface AdminServiceHelper {

	/**
	 * @param findRecentRecords
	 * @return
	 */
	static List<SurveyResponseVO> getSurveyResponseVOFromServeyResponse(List<SurveyResponse> surveyResponseList) {
		return surveyResponseList.stream().map(AdminServiceHelper::getSurveyResponseVOFromSurveyResponse)
				.collect(Collectors.toList());
	}

	/**
	 * @param surveyResponse
	 * @return
	 */
	static SurveyResponseVO getSurveyResponseVOFromSurveyResponse(SurveyResponse surveyResponse) {
		SurveyResponseVO surveyResponseVO = new SurveyResponseVO();
		surveyResponseVO.setUser(UserServiceHelper.getUserVOFromUser(surveyResponse.getUser()));
		surveyResponseVO.setSurvey(SurveyServiceHelper.getSurveyVOFromSurvey(surveyResponse.getSurvey()));
		surveyResponseVO.setResponses(surveyResponse.getResponses().stream()
				.map(AdminServiceHelper::getQuestionResponseVOFromQuestionResponse).collect(Collectors.toList()));
		return surveyResponseVO;
	}

	/**
	 * @param questionResponse
	 * @return
	 */
	static QuestionResponseVO getQuestionResponseVOFromQuestionResponse(QuestionResponse questionResponse) {
		QuestionResponseVO questionResponseVO = new QuestionResponseVO();
		questionResponseVO.setQuestion(SurveyServiceHelper.getQuestionVOFromQuestion(questionResponse.getQuestion()));
		questionResponseVO.setAnswers(questionResponse.getAnswers());
		return questionResponseVO;
	}

}
