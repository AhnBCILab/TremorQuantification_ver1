package com.ahnbcilab.tremorquantification.tremorquantification;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;

public class SpiralResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spiral_result);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Intent intent = getIntent();
        double[] result = intent.getDoubleArrayExtra("result");
        chartOption(result[2], 2f, "chart1","Amplitude");
        chartOption(result[3], 3f, "chart2","Hz");
        chartOption(result[4], 2f, "chart3","Fitting ratio");


        Button Fbtn = (Button)findViewById(R.id.Gosurvey);
        Fbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(SpiralResultActivity.this,
                        SubmitActivity.class);
                startActivity(myIntent);
            }
        });
    }


    private BarChart chartOption(double data1, double data2, String chartname, String index){
        int resID = getResources().getIdentifier(chartname, "id", getPackageName());
        BarChart chart = (HorizontalBarChart) findViewById(resID);

        final ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("오늘 검사");
        xVals.add("최근 검사");


        ArrayList<BarEntry> yVals = new ArrayList<>();
        float bs = 1f;
        yVals.add(new BarEntry(bs,(float)data1));
        yVals.add(new BarEntry(2*bs,(float)data2));
        BarDataSet set1;
        set1 = new BarDataSet(yVals,"data set");
        set1.setDrawValues(true);
        BarData data = new BarData(set1);
        chart.setData(data);

        chart.setDrawGridBackground(false);
        chart.getLegend().setEnabled(false);
        chart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xVals));

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawTopYLabelEntry(false);
//        if (chartname == "chart2")
//            leftAxis.setAxisMaxValue(12f);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setCenterAxisLabels(true);
        chart.setTouchEnabled(false);
        xAxis.setLabelCount(2);
        Description description = new Description();
        description.setText(index);
        chart.setDescription(description);
        chart.animateY(2000, Easing.EasingOption.EaseInOutCubic); //애니메이션
        return chart;
    }
}
