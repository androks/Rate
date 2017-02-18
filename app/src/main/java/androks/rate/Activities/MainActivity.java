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
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

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

//    public void testRequest(View view) {
//        CurrencyManager.with(null).getToday();
//    }

    private void setOnNavigationItemSelectListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.banks:
                                fragment = new BanksFragment();
                                break;

                            case R.id.by_dates:
                                fragment = new ByDatesFragment();
                                break;

                            case R.id.average_today:
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
}
