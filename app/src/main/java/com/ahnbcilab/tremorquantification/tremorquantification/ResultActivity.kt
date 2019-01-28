package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.content.pm.ActivityInfo
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ahnbcilab.tremorquantification.functions.fitting
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_result.*
import kotlin.collections.ArrayList
import com.github.mikephil.charting.data.*



class ResultActivity : AppCompatActivity() {

    private lateinit var mChart: LineChart


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_result)

        val results = intent.extras.getDoubleArray("result")



        mChart = lineChart
        mChart.description.isEnabled = false
        mChart.setDrawGridBackground(false)
        mChart.data = getData(fitting.distance)
        mChart.animateX(3000)

        val l = mChart.legend

        val leftAxis = mChart.axisLeft

        mChart.axisRight.isEnabled = false

        val xAxis = mChart.xAxis
        xAxis.isEnabled = false



       // mean.text = "Mean: ${results[0]}"
       // std.text = "Std: ${results[1]}"
        //standard.text = "Standard: ${results[2]}"
        amp.text = "Amp: ${results[2]}"
        Hz.text = "Hz: ${results[3]}"
        fittingMean.text = "Mean of Fitting distance: ${results[4]}"
       // fittingStd.text = "FittingStd: ${results[5]}"

        Gosurvey.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }
    }

    private fun getData(data: DoubleArray): LineData {

        val sets = ArrayList<ILineDataSet>()
        val entry = ArrayList<Entry>()

        val iter = data.iterator()
        for ((index, value) in iter.withIndex())
            entry.add(Entry(index.toFloat(), value.toFloat()))

//        val ds = ScatterDataSet()
//        ds.color = ColorTemplate.VORDIPLOM_COLORS[3]
//        sets.add(ds)
        val ds = LineDataSet(entry, "distance")
        ds.color = ColorTemplate.VORDIPLOM_COLORS[3]
        ds.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3])
        ds.lineWidth = 2.5f
        ds.circleRadius = 3f

        sets.add(ds)
        return LineData(sets)
//        return ScatterChart(sets)
    }
}
