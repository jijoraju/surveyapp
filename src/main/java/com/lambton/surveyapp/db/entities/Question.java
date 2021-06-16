package com.lambton.surveyapp.db.entities;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;
import com.lambton.surveyapp.db.enums.AnswerType;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 10:51:59 PM
 *
 */

@Getter
@Setter
@Entity
@Table(name = "master_question")
public class Question extends BaseEntity {

	private AnswerType answerType;

	@Column(length = 100000)
	private String title;

	@Column(length = 100000)
	private String description;

	@ElementCollection
	@CollectionTable(name = "option_items", joinColumns = @JoinColumn(name = "question_id"))
	@Column(name = "item")
	private List<String> optionItems;

}
