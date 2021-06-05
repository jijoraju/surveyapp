/**
 * &copyright upski international
 */
package com.lambton.surveyapp.util;

import java.util.Arrays;

/**
 * @author Jijo Raju
 * @Since May 19, 2021 1:02:33 PM
 *
 */
public interface EnumUtil {

	public static <E extends Enum<E>> boolean isInEnum(String value, Class<E> type) {
		return Arrays.stream(type.getEnumConstants()).anyMatch(e -> e.name().equalsIgnoreCase(value.replace("_", " ")));
	}

}
