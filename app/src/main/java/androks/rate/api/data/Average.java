package androks.rate.api.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import androks.rate.api.Pair;
import androks.rate.api.Utils;
import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Average {
	private HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data;

	public Average(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {
		this.data = data;
	}

	public HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> getData() {
		return data;
	}

	/**
	 * Returns the list of dates
	 * @return list of strings depend on SimpleDateFormat param
	 * @param format
	 */
	public List<String> getDatesList(SimpleDateFormat format) {
		Set<String> datesSet = data.keySet();
		List<String> datesList = new ArrayList<>();

		for (String date: datesSet) {
			Calendar calendar = Calendar.getInstance();
			calendar.set(
					Integer.parseInt(date.split("-")[0]),
					//Don't change this line. It's all right
					Integer.parseInt(date.split("-")[1]) - 1,
					Integer.parseInt(date.split("-")[2])
			);
			datesList.add(format.format(calendar.getTime()));
		}
		Collections.sort(datesList, Collections.<String>reverseOrder());
		return datesList;
	}

	/**
	 * Returns average currency rates by date
	 * @param currency code of currency
	 * @param date
	 * @return
	 */
	public CurrencyType getAverageCurrencyTypeByDate(String currency, String date) {
		if (data.containsKey(date)) {
			HashMap<String, HashMap<String, CurrencyType>> dateCurrenciesMap = data.get(date);
			if (dateCurrenciesMap.containsKey(currency)) {
				HashMap<String, CurrencyType> currencyMap = dateCurrenciesMap.get(currency);
				if (currencyMap.containsKey(Utils.CURRENCY_TYPE_AVERAGE)) {
					CurrencyType currencyType =  currencyMap.get(Utils.CURRENCY_TYPE_AVERAGE);
					return currencyType;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the lis of Pairs
	 * @param currency code of currency
	 * @return sorted list of Pairs
	 */
	public List<Pair> getAverageCurrencyListByPeriod(String currency) {
		Set<String> dates = data.keySet();
		List<Pair> pairs = new ArrayList<>();

		for (String date: dates) {
			HashMap<String, HashMap<String, CurrencyType>> dateCurrenciesMap = data.get(date);
			HashMap<String, CurrencyType> currencyMap = dateCurrenciesMap.get(currency);
			CurrencyType currencyType = null;
			if (currencyMap != null) {
				currencyType =  currencyMap.get(Utils.CURRENCY_TYPE_AVERAGE);
			}
			pairs.add(new Pair(Utils.makeCalendar(date), currencyType));
		}
		Collections.sort(pairs);
		return pairs;
	}
}
