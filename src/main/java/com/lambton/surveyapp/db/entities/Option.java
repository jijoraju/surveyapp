/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 10:59:29 PM
 *
 */
@Entity
@Table(name = "master_options")
@Getter
@Setter
public class Option extends BaseEntity{
	
	private String value;

}
