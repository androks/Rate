package androks.rate.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * androks.rate.api.model
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class CurrencyValue {
	@SerializedName("value")
	private String value;
	@SerializedName("diff")
	private float diff;

	public float getValue() {
		return Float.valueOf(value);
	}

	public float getDiff() {
		return diff;
	}

}
