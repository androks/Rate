package androks.rate.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import androks.rate.Fragments.AverageTodayFragment;
import androks.rate.Fragments.BanksFragment;
import androks.rate.Fragments.ByDatesFragment;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Today;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CurrencyManager.Listener{

    private static final String APP_BAR_FOR_BANKS_VIEW = "APP_BAR_FOR_BANKS_VIEW";
    private static final String APP_BAR_FOR_AVERAGE_TODAY_VIEW = "APP_BAR_FOR_AVERAGE_TODAY_VIEW";
    private static final String APP_BAR_FOR_BY_DATES_VIEW = "APP_BAR_FOR_BY_DATES_VIEW";

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar_banks) View banksToolbar;
    @BindView(R.id.appBar) AppBarLayout mAppBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();

        setOnNavigationItemSelectListener();
        setMainFragment();
    }

    public void testRequest(View view) {
        CurrencyManager.with(this).updateToday();
        CurrencyManager.with(this).updateAverage();
    }

    @Override
    public void onTodayReady(Today today) {
        System.out.println(today.getDollar().get(Utils.CURRENCY_TYPE_AVERAGE).average.getValue());
    }

    @Override
    public void onAverageReady(Average average) {
        System.out.println(average.getAverageCurrencyTypeByDate(Utils.CURRENCY_DOLLAR, "2017-02-16").average.getValue());
    }

    @Override
    public void onBanksReady() {

    }

    private void setOnNavigationItemSelectListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.banks:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_BANKS_VIEW);
                                fragment = new BanksFragment();
                                break;

                            case R.id.by_dates:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_BY_DATES_VIEW);
                                fragment = new ByDatesFragment();
                                break;

                            case R.id.average_today:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_AVERAGE_TODAY_VIEW);
                                fragment = new AverageTodayFragment();
                                break;
                        }

                        final FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.container, fragment).commit();

                        return true;
                    }
                });
    }

    private void setMainFragment(){
        fragment = new BanksFragment();
        final FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment).commit();
    }

    static final ButterKnife.Setter<View, String> SET_TOOLBAR_FOR = new ButterKnife.Setter<View, String>() {
        @Override public void set(@NonNull View view, String value, int index) {
            switch (value){
                case APP_BAR_FOR_BANKS_VIEW:
                    view.findViewById(R.id.toolbar_banks).setVisibility(View.VISIBLE);
                    view.findViewById(R.id.toolbar_period).setVisibility(View.GONE);
                    break;
                case APP_BAR_FOR_AVERAGE_TODAY_VIEW:
                    view.findViewById(R.id.toolbar_banks).setVisibility(View.GONE);
                    view.findViewById(R.id.toolbar_period).setVisibility(View.GONE);
                    break;

                case APP_BAR_FOR_BY_DATES_VIEW:
                    view.findViewById(R.id.toolbar_banks).setVisibility(View.GONE);
                    view.findViewById(R.id.toolbar_period).setVisibility(View.VISIBLE);
                    break;
            }
        }
    };
}
