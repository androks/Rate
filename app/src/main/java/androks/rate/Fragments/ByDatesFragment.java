package androks.rate.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import androks.rate.R;
import androks.rate.api.data.Average;
import androks.rate.api.model.CurrencyType;
import butterknife.BindArray;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * Created by androks on 2/17/2017.
 */

public class ByDatesFragment extends Fragment {

    private Unbinder unbinder;
    private HashMap<String, CurrencyType> mValues;
    @BindView(R.id.chart) LineChartView mChart;
    @BindArray(R.array.periods_int) int[] mPeriods;
    Spinner spinner;

    private int mNumberOfPoints;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_by_dates, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        mNumberOfPoints = mPeriods[0];
        spinner = (Spinner) getActivity().findViewById(R.id.period_spinner);
        spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mNumberOfPoints = mPeriods[i];
                //generateData();
            }
        });


        //generateData();
        //resetViewport();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void resetViewport() {
        // Reset viewport height range to (0,100)
        final Viewport v = new Viewport(mChart.getMaximumViewport());
        v.bottom = 0;
        v.top = 100;
        v.left = 0;
        v.right = mNumberOfPoints - 1;
        mChart.setMaximumViewport(v);
        mChart.setCurrentViewport(v);
    }

    private void generateData() {

        ValueShape shape = ValueShape.CIRCLE;

        Line line = new Line();

        List<PointValue> values = new ArrayList<>();
//        for (int j = 0; j < mNumberOfPoints; ++j) {
//            values.add(new PointValue(j, randomNumbersTab[i][j]));
//        }

//        Line line = new Line(values);
//        line.setColor(ChartUtils.COLORS[i]);
        line.setShape(shape);
        line.setCubic(true);
        line.setFilled(true);
        line.setHasLabels(false);
        line.setHasLabelsOnlyForSelected(true);
        line.setHasLines(true);
        line.setHasPoints(true);


        LineChartData data = new LineChartData();

        Axis axisX = new Axis();
        Axis axisY = new Axis().setHasLines(true);
        axisX.setName("Date");
        axisY.setName("Value");
        data.setAxisXBottom(axisX);
        data.setAxisYLeft(axisY);

        data.setBaseValue(Float.NEGATIVE_INFINITY);
        mChart.setValueSelectionEnabled(true);
        mChart.setLineChartData(data);

    }

    private void convertToStack(Average average) {
        Set<String> dates = mValues.keySet();

        Stack<CurrencyType> stack = new Stack<>();

    }
}
