package androks.rate.api;

import java.util.Calendar;

import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Pair implements Comparable{
	public Calendar calendar;
	public CurrencyType currencyType;

	public Pair(Calendar calendar, CurrencyType currencyType) {
		this.calendar = calendar;
		this.currencyType = currencyType;
	}

	@Override
	public int compareTo(Object o) {
		return -1 * calendar.compareTo(((Pair) o).calendar);
	}
}
