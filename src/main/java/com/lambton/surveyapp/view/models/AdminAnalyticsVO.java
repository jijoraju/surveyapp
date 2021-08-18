/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jul 18, 2021 3:33:47 PM
 *
 */

@Getter
@Setter
public class AdminAnalyticsVO {
	
	private Long totalSurveys;
	private Long weeklySurveys;
	private Long totalActiveSurveys;
	private Long weeklyActiveSurveys;
	private Long totalUpcomingSurveys;
	private Long totalSurveyResponses;
	private Long totalUsers;
	private Long newUsers;
	
	List<SurveyResponseVO> recentSurveys;
	List<WeeklyUserSurveyDataVO> weeklyUserSurveyData;
	List<MostActiveUserVO> mostActiveUsers;
	
}
