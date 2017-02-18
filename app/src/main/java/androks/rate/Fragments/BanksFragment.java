package androks.rate.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import androks.rate.Activities.BankActivity;
import androks.rate.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by androks on 2/17/2017.
 */

public class BanksFragment extends Fragment {

    private static final String BANK = "BANK";

    private Unbinder unbinder;
    private ArrayList<String> mBanks = new ArrayList<>();

    @BindView(R.id.banksLV)
    ListView mBanksList;

    @Override public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_banks, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        setUpListView();
        return rootView;
    }

    private void setUpListView() {
        Collections.addAll(mBanks, getActivity().getResources().getStringArray(R.array.banks_array));
        mBanksList.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, mBanks));

        mBanksList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BankActivity.class);
                intent.putExtra(BANK, mBanks.get(i));
                startActivity(intent);
            }
        });
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
