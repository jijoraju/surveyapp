/**
 * &copyright upski international
 */
package com.lambton.surveyapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambton.surveyapp.service.UserService;
import com.lambton.surveyapp.view.models.SurveyResponseVO;
import com.lambton.surveyapp.view.models.SurveyResultVO;
import com.lambton.surveyapp.view.models.UserAnalyticsVO;
import com.lambton.surveyapp.view.models.UserVO;


/**
 * @author Jijo Raju
 * @Since Jun 19, 2021 11:14:13 AM
 *
 */

@RestController
@RequestMapping("/surveyapp")
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping("/user/profile/get")
	public UserVO getUserProfile() {
		return userService.getProfile();  
	}

	@PostMapping("/user/profile/update")
	public UserVO updateUserProfile(@RequestBody UserVO userVO) {
		return userService.updateProfile(userVO);
	}
	
	@PostMapping("/user/survey/submit")
	public SurveyResultVO submitSurveyResponse(@RequestBody SurveyResponseVO surveyResponseVO) {
		return userService.submitSurveyResponse(surveyResponseVO);
	}
	
	@GetMapping("/user/anylytics")
	public UserAnalyticsVO getUserAnalytics() {
		return userService.getUserAnalytics();
	}

}
