package androks.rate.Fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androks.rate.Activities.MainActivity;
import androks.rate.R;
import androks.rate.api.CurrencyManager;
import androks.rate.api.Utils;
import androks.rate.api.data.Average;
import androks.rate.api.data.Banks;
import androks.rate.api.data.Today;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by androks on 2/17/2017.
 */

public class GraphFragment extends Fragment implements CurrencyManager.Listener {

    private Unbinder unbinder;
    private Float[] mValues;
    private Average averageData;
    private int mNumberOfPoints;

    @BindView(R.id.chart)
    LineChartView mChart;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.content)
    LinearLayout linearLayout;
    @BindArray(R.array.periods_int)
    int[] mPeriods;
    Spinner spinner;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_graph, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        setToolbarTitle();
        mNumberOfPoints = mPeriods[0];
        spinner = (Spinner) getActivity().findViewById(R.id.period_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mNumberOfPoints = mPeriods[i];
                generateData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (averageData == null) {
            updateData();
        } else {
            convertCurrenciesToFloat(averageData);
            generateData();
        }
    }

    private void setToolbarTitle() {
        if (getActivity() == null) {
            return;
        }
        String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
        if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.dollar);
        } else {
            ((MainActivity) getActivity()).getSupportActionBar().setTitle(R.string.euro);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_exchange) {
            changeCurrency();
            return true;
        }

        return false;
    }

    private void updateData() {
        showProgress();
        CurrencyManager.with(this).updateAverage();
    }

    private void showProgress() {
        spinner.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
        linearLayout.setVisibility(View.GONE);
    }

    private void hideProgress() {
        spinner.setEnabled(true);
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);
    }

    private void changeCurrency() {
        String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
        if (currentCurrency.equals(Utils.CURRENCY_DOLLAR)) {
            ((MainActivity) getActivity()).currentCurrency = Utils.CURRENCY_EURO;
        } else {
            ((MainActivity) getActivity()).currentCurrency = Utils.CURRENCY_DOLLAR;
        }
        if (averageData != null) {
            convertCurrenciesToFloat(averageData);
            generateData();
        } else {
            updateData();
        }
        setToolbarTitle();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(mChart.getMaximumViewport());
        Float[] temp = mValues;

        Arrays.sort(temp);
        v.bottom = temp[0] - (float) 0.05;
        v.top = temp[temp.length - 1] + (float) 0.05;
        v.left = 0;
        v.right = mNumberOfPoints - (float) 0.8;
        mChart.setMaximumViewport(v);
        mChart.setCurrentViewport(v);

    }

    private void generateData() {
        if (mChart == null) {
            return;
        }

        List<AxisValue> axisDateValues = new ArrayList<>();

        List<String> dates = new ArrayList<>(
                averageData.getDatesList(
                        new SimpleDateFormat(
                                "dd.M",
                                Locale.getDefault()
                        )
                )
        );

        List<PointValue> values = new ArrayList<>();

        for (int i = 0; i < mNumberOfPoints; i++) {
            values.add(new PointValue(i, mValues[i]));
            if (mNumberOfPoints != mPeriods[2])
                axisDateValues.add(new AxisValue((float) i, dates.get(i).toCharArray()));
            else if (i % 2 == 0)
                axisDateValues.add(new AxisValue((float) i, dates.get(i).toCharArray()));
        }

        Line line = new Line(values);
        line.setFormatter(new SimpleLineChartValueFormatter(2));
        line.setColor(ChartUtils.COLOR_BLUE);
        line.setShape(ValueShape.CIRCLE);
        line.setCubic(true);
        line.setFilled(false);
        line.setHasLabels(false);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);

        List<Line> lines = new ArrayList<>();
        lines.add(line);
        LineChartData data = new LineChartData(lines);

        Axis axisY = new Axis()
                .setHasLines(true)
                .setMaxLabelChars(6)
                .setTextColor(Color.BLACK);

        Axis axisX = new Axis(axisDateValues)
                .setTextColor(Color.BLACK);
        data.setAxisYLeft(axisY);
        data.setAxisXBottom(axisX);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        mChart.setValueSelectionEnabled(true);
        mChart.setLineChartData(data);
        resetViewport();

        hideProgress();
    }

    private void convertCurrenciesToFloat(Average average) {
        if (getActivity() != null) {
            String currentCurrency = ((MainActivity) getActivity()).currentCurrency;
            int size = average.getAverageCurrencyListByPeriod(currentCurrency).size();
            mValues = new Float[size];
            for (int i = 0; i < size; i++) {
                mValues[i] = average.getAverageCurrencyListByPeriod(currentCurrency)
                        .get(i)
                        .currencyType
                        .average
                        .getValue();
            }
        }
    }

    @Override
    public void onTodayReady(Today today) {
    }

    @Override
    public void onAverageReady(Average average) {
        if (average != null) {
            averageData = average;
            convertCurrenciesToFloat(average);
            generateData();
        } else
            CurrencyManager.with(this).updateAverage();
    }

    @Override
    public void onBanksReady(Banks banks) {

    }
}
