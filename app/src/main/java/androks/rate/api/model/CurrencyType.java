package androks.rate.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * androks.rate.api.model
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class CurrencyType {
	@SerializedName("buy")
	public CurrencyValue buy;
	@SerializedName("sale")
	public CurrencyValue sale;
	@SerializedName("avg")
	public CurrencyValue average;
}
