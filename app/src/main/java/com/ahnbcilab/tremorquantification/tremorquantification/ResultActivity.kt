package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.PendingIntent.getActivity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.ahnbcilab.tremorquantification.functions.fitting
import com.ahnbcilab.tremorquantification.tremorquantification.R.id.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.github.mikephil.charting.utils.FileUtils
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*
import kotlin.collections.ArrayList

class ResultActivity : AppCompatActivity() {

    private lateinit var mChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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



        mean.text = "Mean: ${results[0]}"
        std.text = "Std: ${results[1]}"
        standard.text = "Standard: ${results[2]}"
        amp.text = "Amp: ${results[3]}"
        Hz.text = "Hz: ${results[4]}"
        fittingMean.text = "FittingMean: ${results[5]}"
        fittingStd.text = "FittingStd: ${results[6]}"

    }

    private fun getData(data: DoubleArray): LineData {

        val sets = ArrayList<ILineDataSet>()
        val entry = ArrayList<Entry>()

        val iter = data.iterator()
        for ((index, value) in iter.withIndex())
            entry.add(Entry(index.toFloat(), value.toFloat()))

        val ds = LineDataSet(entry, "distance")
        ds.color = ColorTemplate.VORDIPLOM_COLORS[3]
        ds.setCircleColor(ColorTemplate.VORDIPLOM_COLORS[3])
        ds.lineWidth = 2.5f
        ds.circleRadius = 3f

        sets.add(ds)

        return LineData(sets)
    }
}
