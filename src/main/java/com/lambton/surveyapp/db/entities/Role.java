/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:05:01 PM
 *
 */

@Entity
@Table(name = "master_role")
@Getter
@Setter
public class Role extends BaseEntity {
	
	@Enumerated(EnumType.STRING)
    private RoleType type;

}
