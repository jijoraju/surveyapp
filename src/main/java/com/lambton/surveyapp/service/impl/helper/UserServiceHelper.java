/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl.helper;

import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.view.models.UserVO;

/**
 * @author Jijo Raju
 * @Since Jun 19, 2021 11:46:15 AM
 *
 */
public interface UserServiceHelper {

	/**
	 * @param user
	 * @return
	 */
	static UserVO getUserVOFromUser(User user) {
		UserVO userVO = new UserVO();
		getUserVOFromUser(userVO, user);
		return userVO;
	}

	/**
	 * @param user
	 * @param userVO
	 * @return
	 */
	static User getUserFromUserVO(User user, UserVO userVO) {
		user.setAddress(userVO.getAddress());
		user.setFirstName(userVO.getFirstname());
		user.setLastname(userVO.getLastname());
		user.setPhoneNumber(userVO.getPhoneNumber());
		return user;
	}

	/**
	 * @param updatedUserVO
	 * @param save
	 */
	static void getUserVOFromUser(UserVO userVO, User user) {
		userVO.setAddress(user.getAddress());
		userVO.setEmail(user.getEmail());
		userVO.setFirstname(user.getFirstName());
		userVO.setLastname(user.getLastname());
		userVO.setPhoneNumber(user.getPhoneNumber());
		userVO.setUsername(user.getEmail());
		userVO.setUniqueId(user.getUniqueId());
	}

}
