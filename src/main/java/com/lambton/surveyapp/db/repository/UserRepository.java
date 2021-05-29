/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lambton.surveyapp.db.entities.User;

/**
 * @author Jijo Raju
 * @Since May 29, 2021 11:17:26 PM
 *
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
