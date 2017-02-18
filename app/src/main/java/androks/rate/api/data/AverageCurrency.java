package androks.rate.api.data;

import java.util.HashMap;

import androks.rate.api.Utils;
import androks.rate.api.model.CurrencyType;

/**
 * androks.rate.api.data
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class AverageCurrency {
	private String label;
	private HashMap<String, CurrencyType> currencyTypes;

	public AverageCurrency(String label, HashMap<String, CurrencyType> currencyTypes) {
		this.label = label;
		this.currencyTypes = currencyTypes;
	}

	public String getLabel() {
		return label;
	}

	public HashMap<String, CurrencyType> getCurrencyTypes() {
		return currencyTypes;
	}

	public CurrencyType getAverage() {
		if (currencyTypes.keySet().contains(Utils.CURRENCY_TYPE_AVERAGE)) {
			return currencyTypes.get(Utils.CURRENCY_TYPE_AVERAGE);
		} else {
			return null;
		}
	}

	public CurrencyType getGovernment() {
		if (currencyTypes.keySet().contains(Utils.CURRENCY_TYPE_GOVERNMENT)) {
			return currencyTypes.get(Utils.CURRENCY_TYPE_GOVERNMENT);
		} else {
			return null;
		}
	}

	public CurrencyType getCommertial() {
		if (currencyTypes.keySet().contains(Utils.CURRENCY_TYPE_COMMERCIAL)) {
			return currencyTypes.get(Utils.CURRENCY_TYPE_COMMERCIAL);
		} else {
			return null;
		}
	}

	public CurrencyType getInterbank() {
		if (currencyTypes.keySet().contains(Utils.CURRENCY_TYPE_INTERBANK)) {
			return currencyTypes.get(Utils.CURRENCY_TYPE_INTERBANK);
		} else {
			return null;
		}
	}

	public CurrencyType getBlack() {
		if (currencyTypes.keySet().contains(Utils.CURRENCY_TYPE_BLACK)) {
			return currencyTypes.get(Utils.CURRENCY_TYPE_BLACK);
		} else {
			return null;
		}
	}

}
