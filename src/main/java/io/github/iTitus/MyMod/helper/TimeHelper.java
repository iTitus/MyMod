package io.github.iTitus.MyMod.helper;

import io.github.iTitus.MyMod.handler.ConfigHandler;

import java.util.Calendar;

public class TimeHelper {

	public static int convertToAMPMIfNecessary(int hour) {

		if (!ConfigHandler.am_pm || (hour < 12 && hour > 0))
			return hour;

		if (hour == 0 || hour == 12)
			hour = 12;
		else
			hour -= 12;

		return hour;

	}

	public static String[] getAllHours() {

		String[] hours = new String[24];

		for (int i = 0; i < hours.length; i++) {
			hours[i] = make2Digits(convertToAMPMIfNecessary(i))
					+ (ConfigHandler.am_pm ? ((i >= 12) ? " PM" : " AM") : "");
		}

		return hours;
	}

	public static String[] getAllMins() {

		String[] mins = new String[60];

		for (int i = 0; i < mins.length; i++) {
			mins[i] = make2Digits(i);
		}

		return mins;
	}

	public static String getAMPM() {
		return ((Calendar.getInstance().get(Calendar.AM_PM) == 0) ? ("AM")
				: ("PM"));
	}

	public static int getHourOfDay() {
		return Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
	}

	public static int getMin() {
		return Calendar.getInstance().get(Calendar.MINUTE);
	}

	public static int getSecs() {
		return Calendar.getInstance().get(Calendar.SECOND);
	}

	public static String getTime() {

		StringBuilder sb = new StringBuilder();

		sb.append(make2Digits(Calendar.getInstance().get(
				(ConfigHandler.am_pm) ? (Calendar.HOUR)
						: (Calendar.HOUR_OF_DAY))));

		sb.append(ConfigHandler.separator);

		sb.append(make2Digits(getMin()));

		if (ConfigHandler.seconds) {

			sb.append(ConfigHandler.separator);

			sb.append(make2Digits(getSecs()));
		}

		if (ConfigHandler.am_pm)
			sb.append(getAMPM());

		return sb.toString();
	}

	public static String getTimeString(int hour, int min) {
		StringBuilder sb = new StringBuilder();

		sb.append(make2Digits(convertToAMPMIfNecessary(hour)));

		sb.append(ConfigHandler.separator);

		sb.append(make2Digits(min));

		if (ConfigHandler.am_pm)
			sb.append(" " + ((hour >= 12) ? ("PM") : ("AM")));

		return sb.toString();
	}

	public static boolean isHour(int hour) {
		return getHourOfDay() == hour;
	}

	public static boolean isMin(int min) {
		return getMin() == min;
	}

	public static String make2Digits(int number) {

		String ret = "" + number;

		if (ret.length() < 2)
			ret = "0" + ret;

		return ret;
	}

}
