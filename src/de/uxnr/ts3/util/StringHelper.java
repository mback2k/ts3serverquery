package de.uxnr.ts3.util;

public class StringHelper {
	public static String formatTimespan(int timespan) {
		String result = "";
		int years = timespan / 31536000;
		if (years > 0) {
			timespan -= years * 31536000;
			result += String.format(" %dy", years);
		}
		int months = timespan / 2592000;
		if (months > 0) {
			timespan -= months * 2592000;
			result += String.format(" %dm", months);
		}
		int days = timespan / 86400;
		if (days > 0) {
			timespan -= days * 86400;
			result += String.format(" %dd", days);
		}
		int hours = timespan / 3600;
		if (hours > 0) {
			timespan -= hours * 3600;
			result += String.format(" %dh", hours);
		}
		int minutes = timespan / 60;
		if (minutes > 0) {
			timespan -= minutes * 60;
			result += String.format(" %dm", minutes);
		}
		if (timespan > 0) {
			result += String.format(" %ds", timespan);
		}
		return result.trim();
	}
}
