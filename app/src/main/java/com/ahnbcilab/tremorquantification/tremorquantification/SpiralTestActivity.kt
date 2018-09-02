package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.CountDownTimer
import android.os.SystemClock
import android.view.MotionEvent
import android.widget.Toast
import com.ahnbcilab.tremorquantification.data.CurrentUserData
import com.ahnbcilab.tremorquantification.data.PathTraceData
import com.ahnbcilab.tremorquantification.functions.Drawable
import kotlinx.android.synthetic.main.activity_spiral_test.*
import java.io.File
import java.io.PrintWriter
import java.util.stream.Collectors

class SpiralTestActivity : AppCompatActivity() {
    private val patientId: Int by lazy { intent.extras.getInt("patientId") }
    private val filename: String by lazy { intent.extras.getString("filename") }

    private var currentX: Float = 0.toFloat()
    private var currentY: Float = 0.toFloat()

    private val pathTrace: MutableList<PathTraceData> = mutableListOf()
    private val timer = object : CountDownTimer(Long.MAX_VALUE, 1000 / 60) {
        override fun onTick(millisUntilFinished: Long) {
            pathTrace.add(PathTraceData(currentX, currentY, (Long.MAX_VALUE - millisUntilFinished).toInt()))
        }

        override fun onFinish() {}
    }

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
            timer.cancel()
            view.saveAsJPG(view, this.filesDir.path + "/spiralTest", "${patientId}_$filename.jpg")

            var prevData: PathTraceData? = null

            if (pathTrace.size > 2) {
                prevData = pathTrace[pathTrace.size - 1]
                for (i in (pathTrace.size - 2) downTo 0) {
                    if (prevData.isSamePosition(pathTrace[i]))
                        pathTrace.removeAt(i)
                    else
                        break
                }
            }

            val metaData= "${CurrentUserData.user?.uid},$patientId,$filename"
            val path = File("${this.filesDir.path}/testData")
            if (!path.exists()) path.mkdirs()
            val file = File(path, "${patientId}_$filename.csv")
            try {
                PrintWriter(file).use { out ->
                    out.println(metaData)
                    for (item in pathTrace)
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

        timer.cancel()
        super.onBackPressed()
    }

    inner class MyView(context: Context) : Drawable(context) {
        private var flag = false
        override fun onTouchEvent(event: MotionEvent): Boolean {
            currentX = event.x
            currentY = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!flag) {
                        flag = true
                        timer.start()
                    }
                }
            }
            return super.onTouchEvent(event)
        }

        override fun clearLayout() {
            super.clearLayout()
            pathTrace.clear()
            timer.cancel()
        }
    }
}