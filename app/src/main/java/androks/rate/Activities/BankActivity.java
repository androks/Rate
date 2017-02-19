package androks.rate.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity implements CurrencyManager.Listener{

    private static final String BANK_ID = "BANK_ID";

    private int mBank;

    @BindView(R.id.recyclerLV) RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);

        mBank = getIntent().getIntExtra(BANK_ID, 0);

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
        banks.getCurrencyTypeListByPeriod(Utils.CURRENCY_DOLLAR, mBank);
    }
}
