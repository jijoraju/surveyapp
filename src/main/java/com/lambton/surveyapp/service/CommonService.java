/**
 * &copyright upski international
 */
package com.lambton.surveyapp.service;

import java.util.List;
import java.util.Map;

import com.lambton.surveyapp.view.models.SearchResultVO;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 11:15:54 AM
 *
 */
public interface CommonService<T> {

	List<T> getAll();

	T findOne(String uniqueId);

	T save(T object);

	T update(T object);

	Void delete(T object);

	SearchResultVO<T> search(Map<String, String> params);

}
