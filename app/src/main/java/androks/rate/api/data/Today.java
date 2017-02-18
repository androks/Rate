package androks.rate.api.data;

import java.util.HashMap;

import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Today {
	private HashMap<String, CurrencyType> dollar;
	private HashMap<String, CurrencyType> euro;

	public Today(HashMap<String, CurrencyType> dollar, HashMap<String, CurrencyType> euro) {
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
