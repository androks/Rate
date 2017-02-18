package androks.rate.api.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

	public List<String> getDatesList() {
		Set<String> datesSet = data.keySet();
		List<String> datesList = new ArrayList<>();

		for (String date: datesSet) {
			datesList.add(date);
		}

		return datesList;
	}

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

	public HashMap<String, CurrencyType> getAverageCurrencyListByPeriod(String currency) {
		Set<String> dates = data.keySet();
		HashMap<String, CurrencyType> result = new HashMap<>();

		for (String date: dates) {
			HashMap<String, HashMap<String, CurrencyType>> dateCurrenciesMap = data.get(date);
			HashMap<String, CurrencyType> currencyMap = dateCurrenciesMap.get(currency);
			CurrencyType currencyType = null;
			if (currencyMap != null) {
				currencyType =  currencyMap.get(Utils.CURRENCY_TYPE_AVERAGE);
			}
			result.put(date, currencyType);
		}
		return result;
	}
}
