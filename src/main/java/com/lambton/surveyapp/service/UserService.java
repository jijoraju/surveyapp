/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service;

import com.lambton.surveyapp.view.models.SurveyResponseVO;
import com.lambton.surveyapp.view.models.SurveyResultVO;
import com.lambton.surveyapp.view.models.UserAnalyticsVO;
import com.lambton.surveyapp.view.models.UserVO;

/**
 * @author Jijo Raju
 * @Since Jun 19, 2021 11:25:08 AM
 *
 */
public interface UserService extends CommonService<UserVO> {
	
	UserVO updateProfile(UserVO userVO);

	/**
	 * @param user
	 * @return
	 */
	UserVO getProfile();

	/**
	 * @param surveyResponseVO
	 * @return
	 */
	SurveyResultVO submitSurveyResponse(SurveyResponseVO surveyResponseVO);

	/**
	 * @return
	 */
	UserAnalyticsVO getUserAnalytics();

}
