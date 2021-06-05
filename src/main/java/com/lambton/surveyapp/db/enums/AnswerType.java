/**
 * &copyright upski international
 */
package com.lambton.surveyapp.db.enums;

import java.util.Arrays;
import java.util.Optional;

import lombok.Getter;

/**
 * @author Jijo Raju
 * @Since Jun 5, 2021 11:24:58 AM
 *
 */
@Getter
public enum AnswerType {

	SINGLE_LINE_TEXT("Single Line Text"), MULTI_LINE_TEXT("Multi Line Text"), RATING_5("Rating (5)"), RATING_10(
			"Rating (10)"), SINGLE_SELECT_OPTION("Single Select Option"), MULTI_SELECT_OPTIONS("Multi Select Options");

	private String desc;

	/**
	 * @param desc
	 */
	private AnswerType(String desc) {
		this.desc = desc;
	}

	public static AnswerType getValueFromDescription(String decription) {
		Optional<AnswerType> type = Arrays.asList(values()).stream().filter(value -> value.getDesc().equals(decription))
				.findFirst();
		if(type.isPresent()) {
			return type.get();
		}else {
			return null;
		}
	}
}
