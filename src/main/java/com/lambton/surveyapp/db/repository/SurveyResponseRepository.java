/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lambton.surveyapp.db.entities.Survey;
import com.lambton.surveyapp.db.entities.SurveyResponse;
import com.lambton.surveyapp.db.entities.User;

/**
 * @author Jijo Raju
 * @Since Jul 16, 2021 8:20:58 PM
 *
 */
public interface SurveyResponseRepository extends JpaRepository<SurveyResponse, Long> {

	Optional<SurveyResponse> findByUniqueId(String uniqueId);

	boolean existsBySurveyAndUser(Survey survey, User user);
	
	List<SurveyResponse> findByUser(User user);
	
	Long countByUser(User user);
	
    Long countByUserAndUpdatedTimeBetween(User user, Calendar today, Calendar sevenDaysBefore);

}
