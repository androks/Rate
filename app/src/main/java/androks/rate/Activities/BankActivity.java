package androks.rate.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import androks.rate.R;
import butterknife.ButterKnife;

public class BankActivity extends AppCompatActivity {

    private static final String BANK = "BANK";

    private String mBank;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank);
        ButterKnife.bind(this);

        mBank = getIntent().getStringExtra("bank");
    }
}
