package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Point
import android.os.SystemClock
import android.view.MotionEvent
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import kotlinx.android.synthetic.main.activity_spiral_test.*
import java.io.File
import java.io.PrintWriter

class SpiralTestActivity : AppCompatActivity() {
    private val patientId = intent.extras.getInt("patientId")
    private val filename = intent.extras.getString("filename")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        setContentView(R.layout.activity_spiral_test)

        val layout = canvasLayout

        val view = MyView(this)

        layout.addView(view)

        resetBtn.setOnClickListener {
            view.clearLayout()
        }

        cancelBtn.setOnClickListener {
            onBackPressed()
        }

        nextBtn.setOnClickListener {
            view.saveAsJPG(view, this.filesDir.path + "/spiralTest", "${patientId}_$filename.jpg")

            val metaData= "[userId],$patientId,$filename"

            val path = File("${this.filesDir.path}/testData")
            if (!path.exists()) path.mkdirs()
            val file = File(path, "${patientId}_$filename.csv")
            try {
                PrintWriter(file).use { out ->
                    out.println(metaData)
                    for (item in view.pathTrace)
                        out.println(item.joinToString(","))
                }
            } catch(e: Exception) {
                Toast.makeText(this, "Error on writing file", Toast.LENGTH_LONG).show()
                println(e.message)
            }

            val intent = Intent(this, SpiralTestListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
            Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show()
        }
    }

    override fun onBackPressed() {
        val file = File(this.filesDir.path + "/signatures/" + filename + ".jpg")
        if (file.exists()) {
            if (file.delete()) println("Delete file $filename")
            else println("Fail to delete $filename")
        }

        super.onBackPressed()
    }

    inner class MyView(context: Context) : Drawable(context) {
        val pathTrace: MutableList<PathTraceData> = mutableListOf()
        private var time: Long = 0

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x: Float = event.x
            val y: Float = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (time == 0.toLong())
                        time = SystemClock.elapsedRealtime()
                    path.moveTo(x, y)
                }
                MotionEvent.ACTION_MOVE -> {
                    path.lineTo(x, y)
                    pathTrace.add(PathTraceData(x, y, (SystemClock.elapsedRealtime() - time).toFloat())) // Unit : ms
                }
            }

            invalidate()
            return true
        }

        override fun clearLayout() {
            super.clearLayout()
            time = 0
            pathTrace.clear()
        }
    }
}