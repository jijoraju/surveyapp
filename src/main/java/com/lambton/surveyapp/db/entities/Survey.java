/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.lambton.surveyapp.annotation.CurrentUser;
import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:08:14 PM
 *
 */

@Entity
@Table(name = "master_survey")
@Getter
@Setter
public class Survey extends BaseEntity {
	
	@Column(length = 100000)
	private String name;
	
	@Column(length = 1000000)
	private String description;
	
	@CurrentUser
	private User createdBy;
	
	private Date expiryDate;
	
	@ElementCollection
	@CollectionTable(name = "survey_tags", joinColumns = @JoinColumn(name = "survey_id"))
	@Column(name = "tags")
	private List<String> tags;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "survey_id")
	private List<Question> questions;

}
