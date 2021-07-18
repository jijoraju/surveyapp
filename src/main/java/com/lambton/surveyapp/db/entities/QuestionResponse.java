/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jul 16, 2021 4:07:47 PM
 *
 */

@Entity
@Table(name = "question_response")
@Getter
@Setter
@PrimaryKeyJoinColumn
public class QuestionResponse extends BaseEntity {

	@OneToOne(targetEntity = Question.class, cascade = CascadeType.ALL)
	private Question question;

	@ElementCollection
	@CollectionTable(name = "question_answers", joinColumns = @JoinColumn(name = "questionresponse_id"))
	@Column(name = "ans")
	List<String> answers;
}
