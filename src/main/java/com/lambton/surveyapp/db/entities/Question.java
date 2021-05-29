package com.lambton.surveyapp.db.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

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
	
	private String title;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "question_id")
	private List<Option> options;

}
