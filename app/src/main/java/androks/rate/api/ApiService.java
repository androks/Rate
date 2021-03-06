package androks.rate.api;

import androks.rate.api.model.ResponseAverage;
import androks.rate.api.model.ResponseBanks;
import androks.rate.api.model.ResponseToday;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

/**
 * androks.rate.api
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public interface ApiService {
	@Headers("X-Mashape-Key: 3p50WQjqYBmsh2T9soUjliqCycqdp1NOr4ZjsnUK2DLNWLd96U")
	@GET("/v1/rates/today")
	Call<ResponseToday> today();

	@Headers("X-Mashape-Key: 3p50WQjqYBmsh2T9soUjliqCycqdp1NOr4ZjsnUK2DLNWLd96U")
	@GET("/v1/rates/averages")
	Call<ResponseAverage> averages();

	@Headers("X-Mashape-Key: 3p50WQjqYBmsh2T9soUjliqCycqdp1NOr4ZjsnUK2DLNWLd96U")
	@GET("/v1/rates/banks")
	Call<ResponseBanks> banks();
}
