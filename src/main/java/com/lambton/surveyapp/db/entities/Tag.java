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
 * @Since Jun 5, 2021 11:23:05 AM
 *
 */
@Getter
@Setter
@Entity
@Table(name = "master_tag")
public class Tag extends BaseEntity {
	
	private String name;

}
