package androks.rate.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androks.rate.Adapters.BanksListViewAdapter;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import androks.rate.api.model.BankItem;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by androks on 2/17/2017.
 */

public class BanksFragment extends Fragment implements CurrencyManager.Listener{

    private static final String BANK = "BANK";

    private List<BankItem> mBankList;
    private Unbinder unbinder;

    @BindView(R.id.banksRV) RecyclerView mBanksRecyclerView;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banks, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        CurrencyManager.with(this).updateBanks();
        return rootView;
    }

    private void setUpListView() {

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mBanksRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBanksRecyclerView.setLayoutManager(mLayoutManager);
        BanksListViewAdapter adapter = new BanksListViewAdapter(getActivity(), mBankList);
        mBanksRecyclerView.setAdapter(adapter);

    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onTodayReady(Today today) {

    }

    @Override
    public void onAverageReady(Average average) {

    }

    @Override
    public void onBanksReady(Banks banks) {
        if (banks != null) {
            mBankList = banks.getBankListByCurrency(Utils.CURRENCY_DOLLAR);
            setUpListView();
        }
    }


}
