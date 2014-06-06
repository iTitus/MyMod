package io.github.iTitus.MyMod.helper;

import io.github.iTitus.MyMod.handler.ConfigHandler;

import java.util.Calendar;

public class TimeHelper {

	public static String getTime() {

		StringBuilder sb = new StringBuilder();

		String hours = ""
				+ Calendar.getInstance().get(
						(ConfigHandler.am_pm) ? (Calendar.HOUR)
								: (Calendar.HOUR_OF_DAY));
		if (hours.length() < 2)
			hours = "0" + hours;
		sb.append(hours);

		sb.append(ConfigHandler.separator);

		String mins = "" + Calendar.getInstance().get(Calendar.MINUTE);
		if (mins.length() < 2)
			mins = "0" + mins;
		sb.append(mins);

		if (ConfigHandler.seconds) {

			sb.append(ConfigHandler.separator);

			String seconds = "" + Calendar.getInstance().get(Calendar.SECOND);
			if (seconds.length() < 2)
				seconds = "0" + seconds;
			sb.append(seconds);
		}

		if (ConfigHandler.am_pm)
			sb.append(" "
					+ ((Calendar.getInstance().get(Calendar.AM_PM) == 0) ? ("AM")
							: ("PM")));

		return sb.toString();
	}
}
