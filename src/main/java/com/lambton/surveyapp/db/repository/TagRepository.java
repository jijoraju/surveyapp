/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lambton.surveyapp.db.entities.Tag;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 12:43:13 PM
 *
 */
public interface TagRepository extends JpaRepository<Tag, Long> {
	
	@Query("select t from Tag t where t.name in (?1)")
	List<Tag> findAllNameIn(List<String> tags);

}
