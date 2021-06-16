/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lambton.surveyapp.db.entities.Role;
import com.lambton.surveyapp.db.entities.RoleType;

/**
 * @author Jijo Raju
 * @Since Jun 12, 2021 9:56:30 PM
 *
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * @param roleUser
	 * @return
	 */
	Role findByType(RoleType roleUser);

}
