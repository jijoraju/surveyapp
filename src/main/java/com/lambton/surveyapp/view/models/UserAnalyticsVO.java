/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jul 18, 2021 8:54:28 AM
 *
 */

@Getter
@Setter
public class UserAnalyticsVO {
	
	private String lastname;
	private String username;
	private String points;
	private String weeklyPoints;
	private String attendedSurvey;
	private String weeklyAttendedSurvey;
	private String availableSurvey;
	private String weeklyAvailableSurvey;
	private String[] populerTopics;
	private String totalTopics;

}
