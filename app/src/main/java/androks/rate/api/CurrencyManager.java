package androks.rate.api;


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
		void onTodayReady();
		void onTodayError();

		void onAverageReady();
		void onAverageError();

		void onBanksReady();
		void onBanksError();
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

	}

	@Override
	public void onTodayError() {

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
