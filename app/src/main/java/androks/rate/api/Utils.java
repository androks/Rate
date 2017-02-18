package androks.rate.api;

import android.content.Context;

import java.util.Calendar;

import androks.rate.R;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Utils {
	public static final String BASE_URL = "https://hryvna-today.p.mashape.com";
	public static final String STATUS_SUCCESS = "success";

	public static final String CURRENCY_DOLLAR = "840";
	public static final String CURRENCY_EURO = "978";

	public static final String CURRENCY_TYPE_INTERBANK = "interbank";
	public static final String CURRENCY_TYPE_GOVERNMENT = "government";
	public static final String CURRENCY_TYPE_COMMERCIAL = "commercial";
	public static final String CURRENCY_TYPE_BLACK = "black";
	public static final String CURRENCY_TYPE_AVERAGE = "avg";

	public static String buildKeyString(int year, int month, int day) {
		StringBuilder builder = new StringBuilder();
		builder.append(year)
				.append("-");

		if (month < 10) {
			builder.append("0");
		}
		builder.append(month)
				.append("-");

		if (day < 10) {
			builder.append("0");
		}
		builder.append(day);

		return builder.toString();
	}

	public static Calendar makeCalendar(String dateString) {
		int year = Integer.valueOf(dateString.substring(0, 4));
		int month = Integer.valueOf(dateString.substring(5, 7));
		int day = Integer.valueOf(dateString.substring(8));

		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(year, month, day);

		return calendar;
	}

	public static String getFriendlyBankTitle(Context context, int bankId){
		int[] bankIds = context.getResources().getIntArray(R.array.bank_integers);
		String[] bankStrings = context.getResources().getStringArray(R.array.bank_strings);

		for(int i = 0; i<bankIds.length; i++)
			if(bankId == bankIds[i])
				return bankStrings[i];
		return "UndefinedBank";
	}
}
