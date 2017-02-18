package androks.rate.api;

import android.util.Log;

import java.util.HashMap;

import androks.rate.api.model.CurrencyType;
import androks.rate.api.model.ResponseAverage;
import androks.rate.api.model.ResponseBanks;
import androks.rate.api.model.ResponseToday;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class ApiManager {
	private static final String TAG = "ApiManager";

	private Retrofit retrofit;
	private ApiService service;
	private Listener listener;

	public interface Listener {
		void onTodayReady(HashMap<String, HashMap<String, CurrencyType>> data);
		void onTodayError();

		void onAverageReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data);
		void onAverageError();

		void onBanksReady(HashMap<String, HashMap<String, HashMap<String, CurrencyType>>> data);
		void onBanksError();
	}

	private ApiManager(Listener listener) {
		this.listener = listener;
		retrofit = new Retrofit.Builder()
				.baseUrl(Utils.BASE_URL)
				.addConverterFactory(GsonConverterFactory.create())
				.build();
		service = retrofit.create(ApiService.class);
	}

	public static ApiManager with(Listener listener) {
		return new ApiManager(listener);
	}

	public void getTodayInfo() {

		Call<ResponseToday> call = service.today();

		call.enqueue(new Callback<ResponseToday>() {
			@Override
			public void onResponse(Call<ResponseToday> call, Response<ResponseToday> response) {
				Log.d(TAG, "onResponse");

				try {
					Log.d(TAG, "null: " + (response.body() == null));
					Log.d(TAG, "null: " + (response.body().data == null));
					Log.d(TAG, "null: " + (response.body().data.isEmpty()));
					HashMap<String, HashMap<String, CurrencyType>> data = response.body().data;
					Log.d(TAG, data.get("840").get("buy").average.value);
				}catch (RuntimeException e) {
					e.printStackTrace();
				}

				ResponseToday responseToday = response.body();
				if (responseToday != null && responseToday.status.equals(Utils.STATUS_SUCCESS)) {
					listener.onTodayReady(responseToday.data);
				} else {
					listener.onTodayError();
				}

			}

			@Override
			public void onFailure(Call<ResponseToday> call, Throwable t) {
				Log.d(TAG, "onFailure");
				listener.onTodayError();
			}
		});
	}

	public void getAverageInfo() {
		Call<ResponseAverage> call = service.averages();

		call.enqueue(new Callback<ResponseAverage>() {
			@Override
			public void onResponse(Call<ResponseAverage> call, Response<ResponseAverage> response) {
				Log.d(TAG, "onResponse");

				try {
					Log.d(TAG, "null: " + (response.body() == null));
					Log.d(TAG, "null: " + (response.body().data == null));
				}catch (RuntimeException e) {
					e.printStackTrace();
				}

				ResponseAverage responseAverage = response.body();
				if (responseAverage != null &&
						responseAverage.status.equals(Utils.STATUS_SUCCESS)) {
					listener.onAverageReady(responseAverage.data);
				} else {
					listener.onAverageError();
				}

			}

			@Override
			public void onFailure(Call<ResponseAverage> call, Throwable t) {
				Log.d(TAG, "onFailure");
				listener.onAverageError();
			}
		});
	}

	public void getBanksInfo() {
		Call<ResponseBanks> call = service.banks();

		call.enqueue(new Callback<ResponseBanks>() {
			@Override
			public void onResponse(Call<ResponseBanks> call, Response<ResponseBanks> response) {
				Log.d(TAG, "onResponse");

				try {
					Log.d(TAG, "null: " + (response.body() == null));
					Log.d(TAG, "null: " + (response.body().data == null));
				}catch (RuntimeException e) {
					e.printStackTrace();
				}

				ResponseBanks responseBanks = response.body();
				if (responseBanks != null &&
						responseBanks.status.equals(Utils.STATUS_SUCCESS)) {
					listener.onBanksReady(responseBanks.data);
				} else {
					listener.onBanksError();
				}

			}

			@Override
			public void onFailure(Call<ResponseBanks> call, Throwable t) {
				Log.d(TAG, "onFailure");
				listener.onBanksError();
			}
		});
	}
}
