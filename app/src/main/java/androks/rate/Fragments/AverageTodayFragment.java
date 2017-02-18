package androks.rate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import androks.rate.api.model.CurrencyType;
import androks.rate.api.model.CurrencyValue;
import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by androks on 2/17/2017.
 */

public class AverageTodayFragment extends Fragment implements CurrencyManager.Listener{

    private Unbinder unbinder;

    @BindView(R.id.average_currency) TextView mAverageCurrency;
    @BindView(R.id.average_currency_diff) TextView mAverageCurrencyDiff;
    @BindView(R.id.banks_currencies_grid_container) LinearLayout mBanksCurrenciesGrigContainer;

    @BindColor(android.R.color.holo_red_dark) int red;
    @BindColor(android.R.color.holo_green_light) int green;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_average_today, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        CurrencyManager.with(this).updateToday();
        super.onStart();
    }

    @Override
    public void onTodayReady(Today today) {
        if(today != null) {
            inflateCommonAverageCurrency(today.getDollar().get(Utils.CURRENCY_TYPE_AVERAGE).average);
            inflateAverageCurrenciesByCommonBanks(today.getDollar());
        }
    }

    private void inflateAverageCurrenciesByCommonBanks(HashMap<String, CurrencyType> currencyTypeHashMap) {
        Set<String> titles = currencyTypeHashMap.keySet();

        for(String title: titles){
            CurrencyType values = currencyTypeHashMap.get(title);
            View bankView = getActivity().getLayoutInflater().inflate(R.layout.item_bank_grig_layout, null);
            ((TextView) bankView.findViewById(R.id.bank)).setText(capitalize(title));
            ((TextView) bankView.findViewById(R.id.ask)).setText(String.format(
                    Locale.getDefault(),
                    "%.2f",
                    values.sale.getValue()));
            ((TextView) bankView.findViewById(R.id.bid)).setText(String.format(
                    Locale.getDefault(),
                    "%.2f",
                    values.buy.getValue()));
            mBanksCurrenciesGrigContainer.addView(bankView);
        }
    }

    private void inflateCommonAverageCurrency(CurrencyValue average) {
        mAverageCurrency.setText(String.format(Locale.getDefault(), "%.2f", average.getValue()));
        String averageCurrencyDiff = String.format(Locale.getDefault(), "%.3f", average.getDiff());
        mAverageCurrencyDiff.setText((average.getDiff() > 0 ? "+" + averageCurrencyDiff : "-" + averageCurrencyDiff));
        mAverageCurrencyDiff.setTextColor(average.getDiff() > 0 ? green:red);
    }

    private String capitalize(final String line) {
        return Character.toUpperCase(line.charAt(0)) + line.substring(1);
    }

    @Override
    public void onAverageReady(Average average) {

    }

    @Override
    public void onBanksReady(Banks banks) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
