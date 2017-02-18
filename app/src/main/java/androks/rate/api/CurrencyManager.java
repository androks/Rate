package androks.rate.api;


import android.util.Log;

import java.util.HashMap;

import androks.rate.api.model.CurrencyType;

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
		void onTodayReady(DataToday dataToday);

		void onAverageReady();

		void onBanksReady();
	}

	private CurrencyManager(Listener listener) {
		this.listener = listener;
	}

	public static CurrencyManager with(Listener listener) {
		return new CurrencyManager(listener);
	}

	public void getToday() {
		ApiManager.with(this).getTodayInfo();

	}

	public void getAverages() {
		ApiManager.with(this).getAverageInfo();
	}

	public void getBanks() {
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

			listener.onTodayReady(new DataToday(dollar, euro));
			System.out.println("Seems ok:)");
		} else {
			listener.onTodayReady(null);
		}

	}

	@Override
	public void onTodayError() {
		listener.onTodayReady(null);
	}

	@Override
	public void onAverageReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {

	}

	@Override
	public void onAverageError() {

	}

	@Override
	public void onBanksReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data) {

	}

	@Override
	public void onBanksError() {

	}
}
