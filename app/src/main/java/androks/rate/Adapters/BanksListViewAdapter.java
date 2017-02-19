package androks.rate.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.Locale;

import androks.rate.R;
import androks.rate.api.Utils;
import androks.rate.api.model.BankItem;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androks on 2/18/2017.
 */

public class BanksListViewAdapter extends RecyclerView.Adapter<BanksListViewAdapter.ViewHolder> {

    private List<BankItem> mValues;
    private Context context;

    public BanksListViewAdapter(Context context, List<BankItem> banks) {
        this.context = context;
        mValues = banks;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BanksListViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        BankItem bankItem = mValues.get(position);

        holder.mBank.setText(Utils.getFriendlyBankTitle(context, bankItem.getBankId()));
        holder.mAsk.setText(String.format(Locale.getDefault(), "%.2f",  bankItem.getSale()));
        holder.mBid.setText(String.format(Locale.getDefault(),"%.2f", bankItem.getBuy()));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @BindView(R.id.bank_title) TextView mBank;
        @BindView(R.id.ask) TextView mAsk;
        @BindView(R.id.bid) TextView mBid;

    }
}
