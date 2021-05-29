/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.lambton.surveyapp.db.entities.common.BaseEntity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:03:05 PM
 *
 */

@Entity
@Table(name = "master_user")
@Getter
@Setter
public class User extends BaseEntity {
	
	private String username;

    private String password;

    private String email;

    private String firstName;

    private String lastname;
    
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

}
