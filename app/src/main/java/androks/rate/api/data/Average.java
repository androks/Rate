package androks.rate.api.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * androks.rate.api.model
 * 18/02/2017
 * Created by Andrii Pohrebniak
 * andrii.pohrebniak@gmail.com
 */

public class Average {
	private HashMap<String, DateAverage> dates;

	public Average(HashMap<String, DateAverage> dates) {
		this.dates = dates;
	}

	public HashMap<String, DateAverage> getDates() {
		return dates;
	}

	public DateAverage getDateAverageByDate(String date) {
		if (dates.keySet().contains(date)) {
			return dates.get(date);
		} else {
			return null;
		}
	}
}
