/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lambton.surveyapp.db.entities.Question;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:17:26 PM
 *
 */

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
	
	Optional<Question> findByUniqueId(String uniqueId);

}
