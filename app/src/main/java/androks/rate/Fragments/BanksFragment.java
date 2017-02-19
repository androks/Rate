package androks.rate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import androks.rate.Activities.MainActivity;
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

import static androks.rate.R.id.progressBar;

/**
 * Created by androks on 2/17/2017.
 */

public class BanksFragment extends Fragment implements CurrencyManager.Listener{

    private static final String BANK = "BANK";

    private List<BankItem> mBankList;
    private Unbinder unbinder;
    private Banks banksData;

    @BindView(R.id.rvBanks) RecyclerView mBanksRecyclerView;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banks, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setToolbarTitle();
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (banksData == null) {
            showProgress();
            CurrencyManager.with(this).updateBanks();
        } else {
            getBankList(banksData);
            setUpListView();
        }
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

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mBanksRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        mBanksRecyclerView.setVisibility(View.VISIBLE);
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
        if (banksData != null) {
            getBankList(banksData);
            setUpListView();
        } else {
            CurrencyManager.with(this).updateBanks();
        }
        setToolbarTitle();
    }

    private void getBankList(Banks banks) {
        if (banks != null && getActivity() != null) {
            String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
            mBankList = banks.getBankListByCurrency(currentCurrency);
        }
    }

    private void setUpListView() {
        if (mBanksRecyclerView == null) {
            return;
        }
        mBanksRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mBanksRecyclerView.setLayoutManager(mLayoutManager);
        BanksListViewAdapter adapter = new BanksListViewAdapter(getActivity(), mBankList);
        mBanksRecyclerView.setAdapter(adapter);
        hideProgress();
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
            getBankList(banks);
            setUpListView();
            banksData = banks;
        }
    }


}
