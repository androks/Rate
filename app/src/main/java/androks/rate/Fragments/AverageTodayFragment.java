package androks.rate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androks.rate.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by androks on 2/17/2017.
 */

public class AverageTodayFragment extends Fragment {

    private Unbinder unbinder;

    @BindView(R.id.average_currency) TextView mAverageCurrency;
    @BindView(R.id.average_currency_diff) TextView mAverageCurrencyDiff;
    @BindView(R.id.banks_currencies_grid_container) LinearLayout mBanksCurrenciesGrigContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_average_today, container, false);
        unbinder = ButterKnife.bind(this, rootView);


        return rootView;
    }
}
