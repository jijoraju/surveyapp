/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.QuestionResponse;
import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.SurveyResponse;
import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.enums.AnswerType;
import com.lambton.surveyapp.db.repository.QuestionRepository;
import com.lambton.surveyapp.db.repository.SurveyRepository;
import com.lambton.surveyapp.db.repository.SurveyResponseRepository;
import com.lambton.surveyapp.db.repository.UserRepository;
import com.lambton.surveyapp.service.UserService;
import com.lambton.surveyapp.service.impl.helper.UserServiceHelper;
import com.lambton.surveyapp.view.models.SearchResultVO;
import com.lambton.surveyapp.view.models.SurveyResponseVO;
import com.lambton.surveyapp.view.models.SurveyResultVO;
import com.lambton.surveyapp.view.models.UserAnalyticsVO;
import com.lambton.surveyapp.view.models.UserVO;

/**
 * @author Jijo Raju
 * @Since Jun 19, 2021 11:29:23 AM
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SurveyRepository surveyRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private SurveyResponseRepository surveyResponseRepository;

	@Override
	public UserVO getProfile() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return UserServiceHelper.getUserVOFromUser(user);
	}

	@Override
	public UserVO updateProfile(UserVO userVO) {
		UserVO updatedUserVO = new UserVO();
		userRepository.findByUniqueId(userVO.getUniqueId()).ifPresentOrElse(existingUser -> {
			User updatedUser = UserServiceHelper.getUserFromUserVO(existingUser, userVO);
			UserServiceHelper.getUserVOFromUser(updatedUserVO, userRepository.save(updatedUser));
		}, () -> {
			throw new RuntimeErrorException(new Error("Operation not permitted"));
		});
		if (StringUtils.hasText(updatedUserVO.getUniqueId()))
			return updatedUserVO;
		else
			throw new RuntimeErrorException(new Error("Operation not permitted"));
	}

	@Override
	public List<UserVO> getAll() {
		return null;
	}

	@Override
	public UserVO findOne(String uniqueId) {
		return null;
	}

	@Override
	public UserVO save(UserVO object) {
		return null;
	}

	@Override
	public UserVO update(UserVO object) {
		return null;
	}

	@Override
	public Void delete(UserVO object) {
		return null;
	}

	@Override
	public SearchResultVO<UserVO> search(Map<String, String> params) {
		return null;
	}

	@Override
	public SurveyResultVO submitSurveyResponse(SurveyResponseVO surveyResponseVO) {
		SurveyResponse surveyResponse = new SurveyResponse();
		surveyResponse.setUniqueId(UUID.randomUUID().toString());
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		surveyResponse.setUser(userRepository.findByUniqueId(user.getUniqueId())
				.orElseThrow(() -> new RuntimeErrorException(new Error("Invalid Survey"))));
		surveyRepository.findByUniqueId(surveyResponseVO.getSurveyId()).ifPresentOrElse(surveyResponse::setSurvey,
				() -> {
					throw new RuntimeErrorException(new Error("Invalid Survey"));
				});
		List<QuestionResponse> questionResponses = surveyResponseVO.getResponses().stream().map(questionResponseVO -> {
			QuestionResponse questionResponse = new QuestionResponse();
			questionResponse.setUniqueId(UUID.randomUUID().toString());
			questionResponse.setQuestion(questionRepository.findByUniqueId(questionResponseVO.getQuestionId())
					.orElseThrow(() -> new RuntimeErrorException(new Error("Invalid Survey"))));
			List<String> answers = new ArrayList<>();
			if (questionResponseVO.getAnswerType().equals(AnswerType.MULTI_SELECT_OPTIONS.getDesc())) {
				answers.addAll(questionResponseVO.getMultipleAnswer());
			}
			else {
				answers.add(questionResponseVO.getSingleAnswer());
			}
			questionResponse.setAnswers(answers);
			return questionResponse;

		}).collect(Collectors.toList());

		surveyResponse.setResponses(questionResponses);
		surveyResponseRepository.save(surveyResponse);
		return null;
	}

	@Override
	public UserAnalyticsVO getUserAnalytics() {
		UserAnalyticsVO userAnalyticsVO = new UserAnalyticsVO();
		User auth = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User user = userRepository.findByUniqueId(auth.getUniqueId())
				.orElseThrow(() -> new RuntimeErrorException(new Error("Invalid User")));
		userAnalyticsVO.setUsername(user.getUsername());
		userAnalyticsVO.setLastname(user.getLastname());

		Calendar today = Calendar.getInstance();
		Calendar sevenDaysBefore = Calendar.getInstance();
		sevenDaysBefore.add(Calendar.DAY_OF_MONTH, -7);

		Long attendedSurveys = surveyResponseRepository.countByUser(user);
		Long weeklyAttendedSurveys = surveyResponseRepository.countByUserAndUpdatedTimeBetween(user, today,
				sevenDaysBefore);

		userAnalyticsVO.setAttendedSurvey(attendedSurveys.toString());
		userAnalyticsVO.setWeeklyAttendedSurvey(weeklyAttendedSurveys.toString());
		userAnalyticsVO.setPoints(String.valueOf(attendedSurveys * 10));
		userAnalyticsVO.setWeeklyPoints(String.valueOf(weeklyAttendedSurveys * 10));

		List<Survey> activeSurveys = surveyRepository.findAllBeforeEndDate(new Date());
		List<Survey> availbleSurveys = activeSurveys.stream()
				.filter(activeSurvey -> !surveyResponseRepository.existsBySurveyAndUser(activeSurvey, user))
				.collect(Collectors.toList());
		userAnalyticsVO.setAvailableSurvey(String.valueOf(availbleSurveys.size()));
		userAnalyticsVO.setWeeklyAvailableSurvey(
				surveyRepository.countByStartDateBetween(today.getTime(), sevenDaysBefore.getTime()).toString());

		List<String> s = surveyResponseRepository.findByUser(user).stream()
				.flatMap(res -> res.getSurvey().getTags().stream()).collect(Collectors.toList());
		Map<String, Long> tags = s.stream().collect(Collectors.groupingBy(w -> w, Collectors.counting()));
		userAnalyticsVO.setTotalTopics(String.valueOf(s.size()));
		String[] popularTags = new String[2];
		userAnalyticsVO.setPopulerTopics(popularTags);
		userAnalyticsVO.setTotalTopics(String.valueOf(tags.size()));
		return userAnalyticsVO;
	}

}
