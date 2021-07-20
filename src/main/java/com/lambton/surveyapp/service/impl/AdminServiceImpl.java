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

import com.lambton.surveyapp.db.entities.RoleType;
import com.lambton.surveyapp.db.entities.SurveyResponse;
import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.repository.SurveyRepository;
import com.lambton.surveyapp.db.repository.SurveyResponseRepository;
import com.lambton.surveyapp.db.repository.UserRepository;
import com.lambton.surveyapp.service.AdminService;
import com.lambton.surveyapp.service.impl.helper.AdminServiceHelper;
import com.lambton.surveyapp.util.DateUtil;
import com.lambton.surveyapp.view.models.AdminAnalyticsVO;
import com.lambton.surveyapp.view.models.MostActiveUserVO;
import com.lambton.surveyapp.view.models.WeeklyUserSurveyDataVO;

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
		adminAnalyticsVO.setWeeklySurveys(
				surveyRepository.countByUpdatedTimeBetween(today.getTime(), sevenDaysBefore.getTime()));
		adminAnalyticsVO.setTotalActiveSurveys(surveyRepository.countAllBeforeEndDate(new Date()));
		adminAnalyticsVO.setWeeklyActiveSurveys(
				surveyRepository.countByStartDateBetween(today.getTime(), sevenDaysBefore.getTime()));
		adminAnalyticsVO.setTotalUpcomingSurveys(surveyRepository.countAllBeforeStartDate(new Date()));
		adminAnalyticsVO.setTotalSurveyResponses(surveyResponseRepository.count());
		List<User> users = userRepository.findAll().stream()
				.filter(u -> !u.getRoles().isEmpty()
						&& u.getRoles().stream().anyMatch(r -> r.getType().equals(RoleType.ROLE_USER)))
				.collect(Collectors.toList());
		adminAnalyticsVO.setTotalUsers(Long.valueOf(users.size()));
		adminAnalyticsVO.setNewUsers(Long.valueOf(users.stream()
				.filter(u -> u.getCreatedDate().after(sevenDaysBefore.getTime())).collect(Collectors.toList()).size()));
		adminAnalyticsVO.setRecentSurveys(
				AdminServiceHelper.getSurveyResponseVOFromServeyResponse(surveyResponseRepository.findRecentRecords()));
		Calendar tenDaysBefore = Calendar.getInstance();
		tenDaysBefore.add(Calendar.DAY_OF_MONTH, -10);
		List<WeeklyUserSurveyDataVO> weeklyUserSurveyDataVOs = surveyResponseRepository
				.findByUpdatedTimeBetween(tenDaysBefore.getTime(), today.getTime()).stream()
				.collect(Collectors.groupingBy(
						surveyRes -> DateUtil.toFormattedString(surveyRes.getUpdatedTime(), "yyyy-MM-dd"),
						Collectors.counting()))
				.entrySet().stream().map(entry -> {
					WeeklyUserSurveyDataVO weeklyUserSurveyDataVO = new WeeklyUserSurveyDataVO();
					weeklyUserSurveyDataVO.setDate(entry.getKey());
					weeklyUserSurveyDataVO.setNoOfUsers(entry.getValue());
					return weeklyUserSurveyDataVO;
				}).collect(Collectors.toList());
		adminAnalyticsVO.setWeeklyUserSurveyData(weeklyUserSurveyDataVOs);

		List<MostActiveUserVO> activeUsers = surveyResponseRepository.findAll().stream()
				.collect(Collectors.groupingBy(SurveyResponse::getUser, Collectors.counting())).entrySet().stream()
				.map(entry -> {
					MostActiveUserVO mostActiveUsersVO = new MostActiveUserVO();
					mostActiveUsersVO.setUser(entry.getKey().getLastname());
					mostActiveUsersVO.setNoOfSurveysAttended(entry.getValue());
					return mostActiveUsersVO;
				}).collect(Collectors.toList());
		adminAnalyticsVO.setMostActiveUsers(activeUsers);
		return adminAnalyticsVO;
	}

}
