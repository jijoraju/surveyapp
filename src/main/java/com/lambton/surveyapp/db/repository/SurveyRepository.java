/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lambton.surveyapp.db.entities.Survey;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:17:26 PM
 *
 */

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long>, JpaSpecificationExecutor<Survey> {
	
	Optional<Survey> findByUniqueId(String uniqueId);
	
	@Query("select s from Survey s where s.startDate >= ?1")
	List<Survey> findAllBeforeStartDate(Date date);
	
	@Query("select count(s) from Survey s where s.startDate >= ?1")
	Long countAllBeforeStartDate(Date date);
	
	@Query("select s from Survey s where s.startDate <= ?1 and s.expiryDate >= ?1")
	List<Survey> findAllBeforeEndDate(Date date);
		
	@Query("select count(s) from Survey s where s.startDate <= ?1 and s.expiryDate >= ?1")
	Long countAllBeforeEndDate(Date today);
	
	Long countByStartDateBetween(Date today, Date sevenDayBefore);
	
	Long countByUpdatedTimeBetween(Date date, Date sevenDayBefore);	
	
}
