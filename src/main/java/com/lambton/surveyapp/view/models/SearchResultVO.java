/**
 * &copyright upski international
 */
package com.lambton.surveyapp.view.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Jijo Raju
 * @Since Jun 6, 2021 12:32:57 PM
 *
 */

@Getter
@Setter
@JsonInclude(value = Include.NON_NULL)
public class SearchResultVO<E> {

	private List<E> searchResult = new ArrayList<>();

	private boolean isLastPage;

}
