package androks.rate.api.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

/**
 * androks.rate.api.model
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class ResponseToday {
	@SerializedName("status")
	public String status;
	@SerializedName("data")
	public HashMap<String, HashMap<String, CurrencyType>> data;
}
