package com.fw.web.support;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateTimeUtil {

	private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	public Date getDate(String dateString) {
		DateFormat df = new SimpleDateFormat("yyyy MM dd", Locale.ENGLISH);
		Date dateObj = null;

		try {
			dateObj = df.parse(dateString);
		} catch (ParseException e) {
			logger.error("Error while coverting string to date");
		}
		return dateObj;
	}

	public Date getDate(String dateString, String format) {
		DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
		Date dateObj = null;

		try {
			dateObj = df.parse(dateString);
		} catch (ParseException e) {
			logger.error("Error while coverting string to date");
		}
		return dateObj;
	}

	public Date getFirstDayOfMonth(String date) {
		return getFirstDayOfMonth(getDate(date));
	}

	public Date getLastDayOfMonth(String date) {
		return getLastDayOfMonth(getDate(date));
	}

	public Date getFirstDayOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfMonth().withMinimumValue().withTimeAtStartOfDay()
				.toDate();
	}

	public Date getLastDayOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfMonth().withMaximumValue()
				.withTime(23, 59, 59, 999).toDate();
	}

	public String formatDate(Date date, String format, String timeZone) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timeZone));
		String dateAsString = simpleDateFormat.format(date);
		return dateAsString;
	}
	
	public static String formatDate(Date date, String format) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);		
		String dateAsString = simpleDateFormat.format(date);
		return dateAsString;
	}

	public Date getNextMonthDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMonths(1).dayOfMonth().withMinimumValue().toDate();
	}

	public Date getFirstDayOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfYear().withMinimumValue().withTimeAtStartOfDay()
				.toDate();
	}

	public Date getLastDayOfYear(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.dayOfYear().withMaximumValue()
				.withTime(23, 59, 59, 999).toDate();
	}

	public Date getNextDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(1).toDate();
	}

	public Date getPrevDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusDays(-1).toDate();
	}

	public Date getNextHour(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.hourOfDay().addToCopy(1).toDate();
	}

	public static Date getLastHourDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.withTime(23, 59, 59, 999).toDate();
	}

	public Date getDateByTimeZone(Date date, String timeZone)
			throws ParseException {
		DateTime dateTime = new DateTime(date);
		return dateTime.withZoneRetainFields(DateTimeZone.forID(timeZone))
				.toDate();
	}

	public Integer getMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.MONTH);
	}

	public Date getQuerterFirstDate(Date date) {

		DateTime dateTime = new DateTime(date);
		int quarter = ((dateTime.getMonthOfYear() - 1) / 3) + 1;

		if (quarter == 1) {
			return dateTime.withDate(dateTime.getYear(), 1, 1)
					.withTimeAtStartOfDay().toDate();
		} else if (quarter == 2) {
			return dateTime.withDate(dateTime.getYear(), 4, 1)
					.withTimeAtStartOfDay().toDate();
		} else if (quarter == 3) {
			return dateTime.withDate(dateTime.getYear(), 7, 1)
					.withTimeAtStartOfDay().toDate();
		} else {
			return dateTime.withDate(dateTime.getYear(), 10, 1)
					.withTimeAtStartOfDay().toDate();
		}
	}

	public Date getQuerterLastDate(Date date) {

		DateTime dateTime = new DateTime(date);
		int quarter = ((dateTime.getMonthOfYear()-1) / 3) + 1;	

		if (quarter == 1) {
			return dateTime.withDate(dateTime.getYear(), 3, 1).dayOfMonth()
					.withMaximumValue().withTime(23, 59, 59, 999).toDate();
		} else if (quarter == 2) {
			return dateTime.withDate(dateTime.getYear(), 6, 1).dayOfMonth()
					.withMaximumValue().withTime(23, 59, 59, 999).toDate();
		} else if (quarter == 3) {
			return dateTime.withDate(dateTime.getYear(), 9, 1).dayOfMonth()
					.withMaximumValue().withTime(23, 59, 59, 999).toDate();
		} else {
			return dateTime.withDate(dateTime.getYear(), 12, 1).dayOfMonth()
					.withMaximumValue().withTime(23, 59, 59, 999).toDate();
		}
	}

	public String getQuarterString(Date date, String timezone) {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone(timezone));
		String dateAsString = simpleDateFormat.format(date);
		String[] splitedString = dateAsString.split("-");

		Integer quarter = ((new Integer(splitedString[1]) + 1) / 3) + 1;

		if (quarter == 1) {
			return splitedString[0] + "-Jan/Feb/Mar";
		} else if (quarter == 2) {
			return splitedString[0] + "-Apr/May/Jun";
		} else if (quarter == 3) {
			return splitedString[0] + "-Jul/Aug/Sep";
		} else
			return splitedString[0] + "-Oct/Nov/Dec";
	}

	public Date getNextQuarterDate(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.monthOfYear().addToCopy(4).toDate();
	}

	public static Date getStartOfDay(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.withTimeAtStartOfDay().toDate();
	}

	public Date addWeek(Date date, int numberOfweeks) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusWeeks(numberOfweeks).toDate();
	}

	public Date getStartOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.withDayOfWeek(1).toDate();
	}

	public Date getEndOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.withDayOfWeek(7).toDate();
	}

	public Integer getDayOfWeek(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfWeek();
	}

	public Date addSeconds(Date date, int seconds) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusSeconds(seconds).toDate();
	}

	public Date addMonths(Date date, int month) {
		DateTime dateTime = new DateTime(date);
		return dateTime.plusMonths(month).toDate();
	}

	public Integer getDayOfMonth(Date date) {
		DateTime dateTime = new DateTime(date);
		return dateTime.getDayOfMonth();
	}

	public Date getChangedDate(Date date, int hours, int mins, int seconds,String timeZone) {
		DateTime dateTime = new DateTime(date);		
		return dateTime.withHourOfDay(hours).withMinuteOfHour(mins)
				.withSecondOfMinute(seconds).withZoneRetainFields(DateTimeZone.forID(timeZone))
				.toDate();
	}

}
