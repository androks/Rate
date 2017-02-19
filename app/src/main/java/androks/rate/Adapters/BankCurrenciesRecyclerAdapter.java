package androks.rate.Adapters;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androks.rate.R;
import androks.rate.api.Pair;
import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by androks on 2/19/2017.
 */

public class BankCurrenciesRecyclerAdapter extends RecyclerView.Adapter<BankCurrenciesRecyclerAdapter.ViewHolder> {

    private List<Pair> mValues;

    public BankCurrenciesRecyclerAdapter(List<Pair> currencies) {
        mValues = currencies;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public BankCurrenciesRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_date_currency, parent, false);
        return new BankCurrenciesRecyclerAdapter.ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }


    @Override
    public void onBindViewHolder(BankCurrenciesRecyclerAdapter.ViewHolder holder, int position) {

        Pair currency = mValues.get(position);
        Calendar calendar = currency.calendar;
        String date = calendar.get(Calendar.DAY_OF_MONTH)
                + "." + calendar.get(Calendar.MONTH)
                + "." + calendar.get(Calendar.YEAR);


        holder.mDate.setText(date);
        holder.mBuy.setText(String.format(Locale.getDefault(), "%.2f",  currency.currencyType.buy.getValue()));
        holder.mSale.setText(String.format(Locale.getDefault(), "%.2f",  currency.currencyType.sale.getValue()));
        holder.mSaleDiff.setText(String.format(Locale.getDefault(), "%.2f",  currency.currencyType.sale.getDiff()));
        holder.mBuyDiff.setText(String.format(Locale.getDefault(), "%.2f",  currency.currencyType.buy.getDiff()));
        holder.mBuyImage.setImageDrawable(currency.currencyType.buy.getDiff()<0?holder.arrowDown:holder.arrowUp);
        holder.mSaleImage.setImageDrawable(currency.currencyType.sale.getDiff()<0?holder.arrowDown:holder.arrowUp);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }

        @BindDrawable(R.drawable.ic_arrow_down_red) Drawable arrowDown;
        @BindDrawable(R.drawable.ic_arrow_upward) Drawable arrowUp;
        @BindView(R.id.date) TextView mDate;
        @BindView(R.id.buy) TextView mBuy;
        @BindView(R.id.sale) TextView mSale;
        @BindView(R.id.sale_diff) TextView mSaleDiff;
        @BindView(R.id.buy_diff) TextView mBuyDiff;
        @BindView(R.id.buy_diff_img) ImageView mBuyImage;
        @BindView(R.id.sale_diff_img) ImageView mSaleImage;
    }
}
