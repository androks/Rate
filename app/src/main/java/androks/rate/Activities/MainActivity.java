package androks.rate.Activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Calendar;
import java.util.List;

import androks.rate.Fragments.AverageTodayFragment;
import androks.rate.Fragments.BanksFragment;
import androks.rate.Fragments.GraphFragment;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Pair;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements CurrencyManager.Listener{

    private static final String APP_BAR_FOR_BANKS_VIEW = "APP_BAR_FOR_BANKS_VIEW";
    private static final String APP_BAR_FOR_AVERAGE_TODAY_VIEW = "APP_BAR_FOR_AVERAGE_TODAY_VIEW";
    private static final String APP_BAR_FOR_BY_DATES_VIEW = "APP_BAR_FOR_BY_DATES_VIEW";

    private int currentFragment = 0;
    public String currentCurrency = Utils.CURRENCY_DOLLAR;

    private Fragment todayFragment;
    private Fragment banksFragment;
    private Fragment byDatesFragment;

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.bottom_navigation) BottomNavigationView bottomNavigationView;
    @BindView(R.id.toolbar_banks) View banksToolbar;
    @BindView(R.id.appBar) AppBarLayout mAppBarLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        todayFragment = new AverageTodayFragment();
        banksFragment = new BanksFragment();
        byDatesFragment = new GraphFragment();


        if (savedInstanceState != null) {
            currentFragment = savedInstanceState.getInt("CURRENT_FRAGMENT");
            currentCurrency = savedInstanceState.getString("CURRENT_CURRENCY");
        }
        setOnNavigationItemSelectListener();
        setCurrentFragment();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("CURRENT_FRAGMENT", currentFragment);
        outState.putString("CURRENT_CURRENCY", currentCurrency);
    }

    public void testRequest(View view) {
        CurrencyManager.with(this).updateToday();
        CurrencyManager.with(this).updateAverage();
        CurrencyManager.with(this).updateBanks();
    }

    @Override
    public void onTodayReady(Today today) {
        System.out.println(today.getDollar().get(Utils.CURRENCY_TYPE_AVERAGE).average.getValue());
    }

    @Override
    public void onAverageReady(Average average) {
        System.out.println(average.getAverageCurrencyListByPeriod(Utils.CURRENCY_DOLLAR).get(0).calendar);
    }

    @Override
    public void onBanksReady(Banks banks) {
        List<Pair> pairs = banks.getCurrencyTypeListByPeriod(Utils.CURRENCY_DOLLAR, 5);
        for (Pair p: pairs) {
            System.out.println(p.calendar.get(Calendar.DAY_OF_MONTH));
        }
    }

    private void setOnNavigationItemSelectListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        boolean changeFragment = false;
                        switch (item.getItemId()){

                            case R.id.banks:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_BANKS_VIEW);
                                changeFragment = (currentFragment == 1);
                                currentFragment = 1;
                                break;

                            case R.id.by_dates:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_BY_DATES_VIEW);
                                changeFragment = (currentFragment == 2);
                                currentFragment = 2;
                                break;

                            case R.id.average_today:
                                ButterKnife.apply(mAppBarLayout, SET_TOOLBAR_FOR, APP_BAR_FOR_AVERAGE_TODAY_VIEW);
                                changeFragment = (currentFragment == 0);
                                currentFragment = 0;
                                break;
                        }

                        if (!changeFragment) {
                            setCurrentFragment();
                        }

                        return true;
                    }
                });
    }

    private void setCurrentFragment(){
        Fragment fragment = todayFragment;
        switch (currentFragment) {
            case 1:
                fragment = banksFragment;
                break;
            case 0:
                fragment = todayFragment;
                break;
            case 2:
                fragment = byDatesFragment;
                break;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container, fragment).commit();
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
