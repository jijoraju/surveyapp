/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambton.surveyapp.db.entities.Role;
import com.lambton.surveyapp.db.entities.RoleType;
import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.repository.SurveyRepository;
import com.lambton.surveyapp.db.repository.SurveyResponseRepository;
import com.lambton.surveyapp.db.repository.UserRepository;
import com.lambton.surveyapp.service.AdminService;
import com.lambton.surveyapp.view.models.AdminAnalyticsVO;

/**
 * @author Jijo Raju
 * @Since Jul 18, 2021 3:57:15 PM
 *
 */
@Service
public class AdminServiceImpl implements AdminService {

	@Autowired
	private SurveyResponseRepository surveyResponseRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private SurveyRepository surveyRepository;

	@Override
	public AdminAnalyticsVO getAdminAnalytics() {
		AdminAnalyticsVO adminAnalyticsVO = new AdminAnalyticsVO();
		adminAnalyticsVO.setTotalSurveys(surveyRepository.count());

		Calendar today = Calendar.getInstance();
		Calendar sevenDaysBefore = Calendar.getInstance();
		sevenDaysBefore.add(Calendar.DAY_OF_MONTH, -7);
		adminAnalyticsVO.setWeeklySurveys(surveyRepository.countByUpdatedTimeBetween(today, sevenDaysBefore));
		adminAnalyticsVO.setTotalActiveSurveys(surveyRepository.countAllBeforeEndDate(new Date()));
		adminAnalyticsVO.setWeeklyActiveSurveys(
				surveyRepository.countByStartDateBetween(today.getTime(), sevenDaysBefore.getTime()));
		adminAnalyticsVO.setTotalUpcomingSurveys(surveyRepository.countAllBeforeStartDate(new Date()));
		adminAnalyticsVO.setTotalSurveyResponses(surveyResponseRepository.count());
		List<User> users = userRepository.findAll().stream().filter(u -> u.getRoles().contains(RoleType.ROLE_USER))
				.collect(Collectors.toList());
		adminAnalyticsVO.setTotalUsers(Long.valueOf(users.size()));
		adminAnalyticsVO.setNewUsers(Long.valueOf(users.stream()
				.filter(u -> u.getCreatedDate().compareTo(sevenDaysBefore) == 1).collect(Collectors.toList()).size()));
		return null;
	}

}
