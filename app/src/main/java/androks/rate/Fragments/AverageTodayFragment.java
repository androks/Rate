package androks.rate.Fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

import androks.rate.Activities.MainActivity;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import androks.rate.api.model.CurrencyType;
import androks.rate.api.model.CurrencyValue;
import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by androks on 2/17/2017.
 */

public class AverageTodayFragment extends Fragment implements CurrencyManager.Listener{

    private Unbinder unbinder;
    private HashMap<String,String> commonBanksLabels = new HashMap<>();
    private Today todayData = null;

    @BindView(R.id.content) LinearLayout linearLayout;
    @BindView(R.id.average_currency) TextView mAverageCurrency;
    @BindView(R.id.average_currency_diff) TextView mAverageCurrencyDiff;
    @BindView(R.id.banks_currencies_grid_container) LinearLayout mBanksCurrenciesGrigContainer;
    @BindView(R.id.imgArrow) ImageView imgArrow;
    @BindView(R.id.tvOne) TextView tvOne;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @BindColor(R.color.material_red) int red;
    @BindColor(R.color.material_green) int green;

    @BindDrawable(R.drawable.ic_arrow_down_red) Drawable arrowDown;
    @BindDrawable(R.drawable.ic_arrow_upward) Drawable arrowUp;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_average_today, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        setToolbarTitle();
        initKeyMap();
        return rootView;
    }

    @Override
    public void onStart() {
        if (todayData == null) {
            updateData();
        } else {
            inflateViews(todayData);
        }
        super.onStart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exchange) {
            changeCurrency();
            return true;
        }

        return false;
    }

    private void updateData() {
        showProgress();
        CurrencyManager.with(this).updateToday();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void setToolbarTitle() {
        if (getActivity() == null) {
            return;
        }
        String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
        if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.dollar);
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.euro);
        }
    }

    private void changeCurrency() {
        String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
        if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
            ((MainActivity) getActivity()).currentCurrency = Utils.CURRENCY_EURO;
        } else {
            ((MainActivity) getActivity()).currentCurrency = Utils.CURRENCY_DOLLAR;
        }
        if (todayData != null) {
            inflateViews(todayData);
        } else {
            updateData();
        }
        setToolbarTitle();
    }

    private void inflateViews(Today today) {
        if(today != null && getActivity() != null) {
            String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
            if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
                inflateCommonAverageCurrency(today.getDollar().get(Utils.CURRENCY_TYPE_AVERAGE).average);
                inflateAverageCurrenciesByCommonBanks(today.getDollar());
            } else {
                inflateCommonAverageCurrency(today.getEuro().get(Utils.CURRENCY_TYPE_AVERAGE).average);
                inflateAverageCurrenciesByCommonBanks(today.getEuro());
            }
            hideProgress();
        }
    }

    @Override
    public void onTodayReady(Today today) {
        if(today != null) {
            inflateViews(today);
            todayData = today;
        }
    }

    private void initKeyMap() {
        String[] labelsArr = getResources().getStringArray(R.array.common_banks_lables);
        String[] labelsKeys = getResources().getStringArray(R.array.common_banks_keys);
        for (int i = 0; i < labelsArr.length; i++) {
            commonBanksLabels.put(labelsKeys[i], labelsArr[i]);
        }
    }

    private void inflateAverageCurrenciesByCommonBanks(HashMap<String,
            CurrencyType> currencyTypeHashMap) {
        Set<String> titles = currencyTypeHashMap.keySet();
        mBanksCurrenciesGrigContainer.removeAllViewsInLayout();

        for(String title: titles){
            CurrencyType values = currencyTypeHashMap.get(title);
            View bankView = getActivity().getLayoutInflater().inflate(R.layout.item_bank_grig_layout, null);

            ((TextView) bankView.findViewById(R.id.bank)).setText(commonBanksLabels.get(title));
            ((TextView) bankView.findViewById(R.id.ask)).setText(String.format(
                    Locale.getDefault(),
                    "%.2f",
                    values.sale.getValue()));
            ((TextView) bankView.findViewById(R.id.bid)).setText(String.format(
                    Locale.getDefault(),
                    "%.2f",
                    values.buy.getValue()));

            ((TextView) bankView.findViewById(R.id.ask))
                    .setTextColor((values.sale.getDiff() > 0 ? green : red));
            ((TextView) bankView.findViewById(R.id.bid))
                    .setTextColor((values.buy.getDiff() > 0 ? green : red));
            mBanksCurrenciesGrigContainer.addView(bankView);
        }
    }

    private void inflateCommonAverageCurrency(CurrencyValue average) {
        String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
        mAverageCurrency.setText(String.format(Locale.getDefault(), "%.2f", average.getValue()));
        String averageCurrencyDiff = String.format(Locale.getDefault(), "%.2f", average.getDiff());
        imgArrow.setImageDrawable((average.getDiff() > 0 ? arrowUp : arrowDown));
        tvOne.setText(currentCurrency.equals(Utils.CURRENCY_DOLLAR)
                ? R.string.one_dollar : R.string.one_euro);
        mAverageCurrencyDiff.setText(averageCurrencyDiff);
        mAverageCurrencyDiff.setTextColor(average.getDiff() > 0 ? green:red);
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
