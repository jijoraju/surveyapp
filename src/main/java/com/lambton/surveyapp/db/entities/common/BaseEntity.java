/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities.common;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 10:53:08 PM
 *
 */

@Getter
@Setter
@MappedSuperclass
public class BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false, nullable = false)
	private Long id;

	@Column(name = "uniqueId", insertable = true, updatable = false, nullable = false, unique = true)
	private String uniqueId;
	
	@CreationTimestamp
	@Column(updatable = false)
	private Date createdDate;
	
	@UpdateTimestamp
	private Date updatedTime;

}
