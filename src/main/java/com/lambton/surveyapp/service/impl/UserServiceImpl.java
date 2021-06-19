/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service.impl;

import java.util.List;
import java.util.Map;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lambton.surveyapp.db.entities.User;
import com.lambton.surveyapp.db.repository.UserRepository;
import com.lambton.surveyapp.service.UserService;
import com.lambton.surveyapp.service.impl.helper.UserServiceHelper;
import com.lambton.surveyapp.view.models.SearchResultVO;
import com.lambton.surveyapp.view.models.UserVO;

/**
 * @author Jijo Raju
 * @Since Jun 19, 2021 11:29:23 AM
 *
 */

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserVO getProfile() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return UserServiceHelper.getUserVOFromUser(user);
	}

	@Override
	public UserVO updateProfile(UserVO userVO) {
		UserVO updatedUserVO = new UserVO();
		userRepository.findByUniqueId(userVO.getUniqueId()).ifPresentOrElse(existingUser -> {
			User updatedUser = UserServiceHelper.getUserFromUserVO(existingUser, userVO);
			UserServiceHelper.getUserVOFromUser(updatedUserVO, userRepository.save(updatedUser));
		}, () -> {
			throw new RuntimeErrorException(new Error("Operation not permitted"));
		});
		if (StringUtils.hasText(updatedUserVO.getUniqueId()))
			return updatedUserVO;
		else
			throw new RuntimeErrorException(new Error("Operation not permitted"));
	}

	@Override
	public List<UserVO> getAll() {
		return null;
	}

	@Override
	public UserVO findOne(String uniqueId) {
		return null;
	}

	@Override
	public UserVO save(UserVO object) {
		return null;
	}

	@Override
	public UserVO update(UserVO object) {
		return null;
	}

	@Override
	public Void delete(UserVO object) {
		return null;
	}

	@Override
	public SearchResultVO<UserVO> search(Map<String, String> params) {
		return null;
	}

}
