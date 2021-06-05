/**
 * &copyright upski international
 */
package com.lambton.surveyapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lambton.surveyapp.service.SurveyService;
import com.lambton.surveyapp.view.models.SurveyVO;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 11:11:05 AM
 *
 */

@RestController
@RequestMapping("/surveyapp")
public class AdminController {

	@Autowired
	private SurveyService surveyService;

	@GetMapping("/survey/all")
	public List<SurveyVO> findAllSurveys() {
		return surveyService.getAll();
	}

	@GetMapping("/survey/get")
	public SurveyVO createSurvey(@RequestParam String surveyId) {
		return surveyService.findOne(surveyId);
	}

	@PostMapping("/survey/create")
	public SurveyVO createSurvey(@RequestBody SurveyVO surveyVO) {
		return surveyService.save(surveyVO);
	}

}
