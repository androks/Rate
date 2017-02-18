package androks.rate.api.data;

import java.util.HashMap;

import androks.rate.api.Utils;

/**
 * androks.rate.api.data
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class DateAverage {
	private String stringDate;
	private HashMap<String, AverageCurrency> averageCurrencyMap;

	public DateAverage(String stringDate, HashMap<String, AverageCurrency> averageCurrencyMap) {
		this.stringDate = stringDate;
		this.averageCurrencyMap = averageCurrencyMap;
	}

	public String getStringDate() {
		return stringDate;
	}

	public HashMap<String, AverageCurrency> getAverageCurrencyMap() {
		return averageCurrencyMap;
	}

	public AverageCurrency getDollar() {
		if (averageCurrencyMap.keySet().contains(Utils.CURRENCY_DOLLAR)) {
			return averageCurrencyMap.get(Utils.CURRENCY_DOLLAR);
		} else {
			return null;
		}
	}

	public AverageCurrency getEuro() {
		if (averageCurrencyMap.keySet().contains(Utils.CURRENCY_EURO)) {

			return averageCurrencyMap.get(Utils.CURRENCY_EURO);
		} else {
			return null;
		}
	}

	public AverageCurrency getAverageCurrencyByCurrencyCode(String currency) {
		if (averageCurrencyMap.keySet().contains(currency)) {
			return averageCurrencyMap.get(currency);
		} else {
			return null;
		}
	}
}
