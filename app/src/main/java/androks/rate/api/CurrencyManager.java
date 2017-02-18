package androks.rate.api;


import java.util.HashMap;

import androks.rate.api.data.Average;
import androks.rate.api.model.CurrencyType;
import androks.rate.api.data.Today;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class CurrencyManager implements ApiManager.Listener{
	private static final String TAG = "CurrencyManager";

	private Listener listener;

	public interface Listener {
		void onTodayReady(Today today);

		void onAverageReady(Average average);

		void onBanksReady();
	}

	private CurrencyManager(Listener listener) {
		this.listener = listener;
	}

	public static CurrencyManager with(Listener listener) {
		return new CurrencyManager(listener);
	}

	public void updateToday() {
		ApiManager.with(this).getTodayInfo();
	}

	public void updateAverage() {
		ApiManager.with(this).getAverageInfo();
	}

	public void updateBanks() {
		ApiManager.with(this).getBanksInfo();
	}

	@Override
	public void onTodayReady(HashMap<String, HashMap<String, CurrencyType>> data) {
		if (listener == null) {
			return;
		}

		if (data != null) {
			HashMap<String, CurrencyType> dollar = null;
			HashMap<String, CurrencyType> euro = null;

			if (data.keySet().contains(Utils.CURRENCY_DOLLAR)) {
				dollar = data.get(Utils.CURRENCY_DOLLAR);
			}
			if (data.keySet().contains(Utils.CURRENCY_EURO)) {
				euro = data.get(Utils.CURRENCY_EURO);
			}

			listener.onTodayReady(new Today(dollar, euro));
		} else {
			listener.onTodayReady(null);
		}

	}

	@Override
	public void onTodayError() {
		if (listener != null) {
			listener.onTodayReady(null);
		}
	}

	@Override
	public void onAverageReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {
		if (listener == null) {
			return;
		}

		if (data != null) {
			Average average = new Average(data);
			listener.onAverageReady(average);
		} else {
			listener.onAverageReady(null);
		}

	}

	@Override
	public void onAverageError() {
		if (listener != null) {
			listener.onAverageReady(null);
		}
	}

	@Override
	public void onBanksReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {

	}

	@Override
	public void onBanksError() {

	}
}
