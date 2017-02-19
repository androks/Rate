package androks.rate.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

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

    @BindView(R.id.recyclerLV) RecyclerView mRecyclerView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        mBankId = getIntent().getIntExtra(BANK_ID, 0);
        toolbar.setTitle(Utils.getFriendlyBankTitle(getApplicationContext(), mBankId));

        CurrencyManager.with(this).updateBanks();
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
            mCurrencyList = banks.getCurrencyTypeListByPeriod(Utils.CURRENCY_DOLLAR, mBankId);
            setUpRecyclerView();
        } else
            CurrencyManager.with(this).updateBanks();
    }
    private void setUpRecyclerView() {
            if (mRecyclerView == null) {
                return;
            }

            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

            // use a linear layout manager
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
            BankCurrenciesRecyclerAdapter adapter = new BankCurrenciesRecyclerAdapter(mCurrencyList);
        mRecyclerView.setAdapter(adapter);
    }
}
