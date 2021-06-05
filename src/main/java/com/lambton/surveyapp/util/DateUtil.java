/**
 * &copyright upski international
 */
package com.lambton.surveyapp.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.management.RuntimeErrorException;

/**
 * @author Jijo Raju
 * @Since May 4, 2021 2:43:56 PM
 *
 */
public interface DateUtil {

	/**
	 * @param startDate
	 * @param string
	 * @return
	 */
	static Date praseFromString(String value, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern);
		try {
			return formatter.parse(value);
		}
		catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeErrorException(new Error("Error in parse"));
		}
	}

	/**
	 * @param startDate
	 * @param string
	 * @return
	 */
	static String toFormattedString(Date value, String pattern) {
		DateFormat formatter = new SimpleDateFormat(pattern);
		return formatter.format(value);
	}

}
