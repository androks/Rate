package androks.rate.api.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import androks.rate.api.Pair;
import androks.rate.api.Utils;
import androks.rate.api.model.BankItem;
import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api.data
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Banks {
	private HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data;

	public Banks(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {
		this.data = data;
	}

	/**
	 * Returns a list of BankItems depending on currency
	 * @param currency code of currency
	 * @return
	 */
	public List<BankItem> getBankListByCurrency(String currency) {
		List<BankItem> resultList = new ArrayList<>();

		if (data == null) {
			return resultList;
		}

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DAY_OF_MONTH);

		String dateKey = Utils.buildKeyString(year, month, day);

		if (!data.containsKey(dateKey)){
			dateKey = Utils.buildKeyString(year,month,day - 1);
		}

		if (data.containsKey(dateKey)) {
			HashMap<String, HashMap<String, CurrencyType>> dateCurrencyMap = data.get(dateKey);
			if (dateCurrencyMap.containsKey(currency)) {
				HashMap<String, CurrencyType> dateCurrency = dateCurrencyMap.get(currency);
				for (String bankKey: dateCurrency.keySet()) {
					int bankId = Integer.valueOf(bankKey);
					float buy = dateCurrency.get(bankKey).buy.getValue();
					float buyDiff = dateCurrency.get(bankKey).buy.getDiff();
					float sale = dateCurrency.get(bankKey).sale.getValue();
					float sellDiff = dateCurrency.get(bankKey).sale.getDiff();
					BankItem bankItem = new BankItem(bankId, buy, buyDiff, sale, sellDiff);
					resultList.add(bankItem);
				}
			}

		}
		return resultList;
	}

	/**
	 * Returns the list of dates
	 * @return list of strings like "2017-02-18"
	 */
	public List<String> getDatesList() {
		Set<String> datesSet = data.keySet();
		List<String> datesList = new ArrayList<>();

		for (String date: datesSet) {
			datesList.add(date);
		}

		return datesList;
	}

	public CurrencyType getCurrencyTypeByDate(String currency, String date, int bankID) {
		CurrencyType currencyType = null;
		String stringBankID = String.valueOf(bankID);
		if (data.containsKey(date)) {
			HashMap<String, HashMap<String, CurrencyType>> dateMap = data.get(date);
			if (dateMap.containsKey(currency)) {
				HashMap<String, CurrencyType> currencyMap = dateMap.get(currency);
				if (currencyMap.containsKey(stringBankID)) {
					currencyType =  currencyMap.get(stringBankID);
				}
			}
		}
		return currencyType;
	}

	/**
	 * Returns the lis of Pairs
	 * @param currency code of currency
	 * @param bankID code of bank
	 * @return sorted list of Pairs
	 */
	public List<Pair> getCurrencyTypeListByPeriod(String currency, int bankID) {
		String stringBankID = String.valueOf(bankID);
		Set<String> dates = data.keySet();
		List<Pair> pairs = new ArrayList<>();

		for (String date: dates) {
			HashMap<String, HashMap<String, CurrencyType>> dateMap = data.get(date);
			HashMap<String, CurrencyType> currencyMap = dateMap.get(currency);
			if (currencyMap != null) {
				CurrencyType currencyType = currencyMap.get(stringBankID);
				if (currencyType != null) {
					pairs.add(new Pair(Utils.makeCalendar(date), currencyType));
				}
			}
		}
		Collections.sort(pairs);
		return pairs;
	}
}
