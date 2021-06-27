/**
 * &copyright upski international
 */
package com.lambton.surveyapp.util;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.util.StringUtils;

/**
 * @author Jijo Raju
 * @Since Jun 20, 2021 7:41:11 PM
 *
 */
public interface StringUtil {

	static String capitalize(final String value) {
		if (!StringUtils.hasText(value))
			return "";
		return Stream.of(value.split(" ")).map(word ->  Character.toUpperCase(value.charAt(0)) + value.substring(1).toLowerCase())
				.collect(Collectors.joining(" "));
	}

}
