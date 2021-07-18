/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jun 27, 2021 10:40:21 AM
 *
 */
@Entity
@Table(name = "master_survey_response")
@Getter
@Setter
public class SurveyResponse extends BaseEntity {
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "survey_id")
	private Survey survey;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "survey_response_id")
	List<QuestionResponse> responses;

}
