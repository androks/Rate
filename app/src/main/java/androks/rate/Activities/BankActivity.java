package androks.rate.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import androks.rate.Adapters.BankCurrenciesRecyclerAdapter;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Pair;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity implements CurrencyManager.Listener{

    private static final String BANK_ID = "BANK_ID";

    private int mBankId;
    private List<Pair> mCurrencyList;
    private String currentCurrency = Utils.CURRENCY_DOLLAR;
    private Banks banksData;

    @BindView(R.id.recyclerLV) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        mBankId = getIntent().getIntExtra(BANK_ID, 0);
        getSupportActionBar().setTitle(Utils.getFriendlyBankTitle(getApplicationContext(), mBankId));

        showProgress();
        updateData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragment_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exchange) {
            changeCurrency();
            return true;
        }
        return false;
    }

    private void changeCurrency() {
        if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
            currentCurrency = Utils.CURRENCY_EURO;
        } else {
            currentCurrency = Utils.CURRENCY_DOLLAR;
        }
        if (banksData != null) {
            setmCurrencyList(banksData);
            setUpRecyclerView();
        } else {
            updateData();
        }
    }

    private void updateData() {
        showProgress();
        CurrencyManager.with(this).updateBanks();
    }

    private void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.GONE);
    }

    private void hideProgress() {
        progressBar.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onTodayReady(Today today) {

    }

    @Override
    public void onAverageReady(Average average) {

    }

    @Override
    public void onBanksReady(Banks banks) {

        if(banks != null) {
            setmCurrencyList(banks);
            setUpRecyclerView();
        } else
            updateData();


        banksData = banks;
    }

    private void setmCurrencyList(Banks banks) {
        mCurrencyList = banks.getCurrencyTypeListByPeriod(currentCurrency, mBankId);
    }

    private void setUpRecyclerView() {
        if (mRecyclerView == null) {
            return;
        }
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        BankCurrenciesRecyclerAdapter adapter = new BankCurrenciesRecyclerAdapter(mCurrencyList);
        mRecyclerView.setAdapter(adapter);
        hideProgress();
    }
}
