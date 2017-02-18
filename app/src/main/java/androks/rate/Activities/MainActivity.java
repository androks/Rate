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
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private Fragment fragment;
    private FragmentManager fragmentManager;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    @BindView(R.id.toolbar_banks)
    View banksToolbar;

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

    private void setOnNavigationItemSelectListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.banks:
                                banksToolbar.setVisibility(View.VISIBLE);
                                fragment = new BanksFragment();
                                break;

                            case R.id.by_dates:
                                banksToolbar.setVisibility(View.GONE);
                                fragment = new ByDatesFragment();
                                break;

                            case R.id.average_today:
                                banksToolbar.setVisibility(View.GONE);
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
