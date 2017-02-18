package androks.rate.api;

import java.util.HashMap;

import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class DataToday {
	private HashMap<String, CurrencyType> dollar;
	private HashMap<String, CurrencyType> euro;

	public DataToday(HashMap<String, CurrencyType> dollar, HashMap<String, CurrencyType> euro) {
		this.dollar = dollar;
		this.euro = euro;
	}

	public HashMap<String, CurrencyType> getDollar() {
		return dollar;
	}

	public HashMap<String, CurrencyType> getEuro() {
		return euro;
	}
}
