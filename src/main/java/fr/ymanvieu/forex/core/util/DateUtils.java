/**
 * Copyright (C) 2015 Yoann Manvieu
 *
 * This software is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package fr.ymanvieu.forex.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.Months;

public class DateUtils {

	public static final String DT_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS z";

	/**
	 * Date parser with format: yyyy-MM-dd HH:mm:ss.SSS z
	 */
	public static final SimpleDateFormat DATE_TIME_WITH_TZ = new SimpleDateFormat(DT_PATTERN);

	/**
	 * Parses a string with expected format: yyyy-MM-dd HH:mm:ss.SSS z
	 * 
	 * @return the corresponding date object
	 */
	public static Date parse(String dateStr) throws ParseException {
		return DATE_TIME_WITH_TZ.parse(dateStr);
	}

	/**
	 * Extract date and time from the given date and returns a date object with the same date/time on the UTC time zone.
	 *
	 * @param date
	 */
	public static Date toUTC(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		Calendar utcCal = Calendar.getInstance();
		utcCal.setTimeZone(TimeZone.getTimeZone("UTC"));

		utcCal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY),
				cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND));
		utcCal.set(Calendar.MILLISECOND, cal.get(Calendar.MILLISECOND));

		return utcCal.getTime();
	}

	/**
	 * Returns a new date with 1 day after the given date (others fields are untouched)
	 * 
	 * @param date
	 */
	public static Date nextDay(Date date) {
		Calendar nextDayFirstDetectionDate = Calendar.getInstance();
		nextDayFirstDetectionDate.setTime(date);
		nextDayFirstDetectionDate.add(Calendar.DAY_OF_MONTH, 1);
		return nextDayFirstDetectionDate.getTime();
	}

	/**
	 * @param date1
	 * @param date2
	 * @return the number of months between the two dates
	 */
	public static int getNbOfMonths(Date date1, Date date2) {
		DateTime startDt = new DateTime(date1.getTime());
		DateTime endDt = new DateTime(date2.getTime());

		return Months.monthsBetween(startDt, endDt).getMonths();
	}
}
