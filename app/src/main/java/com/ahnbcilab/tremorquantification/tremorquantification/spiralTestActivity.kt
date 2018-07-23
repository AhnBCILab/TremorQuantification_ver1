package com.ahnbcilab.tremorquantification.tremorquantification

import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.os.SystemClock
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_spiral_test.*

class spiralTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spiral_test)

        val layout = canvasLayout

        val view = MyView(this)

        layout.addView(view)
    }

    inner class MyView(context: Context) : View(context) {
        private val paint = Paint()
        private val path = Path()
        val timerLayout = timer
        var count: Int = 0
        var flag: Boolean = false
        var time: Long = 0

        private var location: IntArray = intArrayOf(0, 0)

        init {
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 8f

            canvasLayout.getLocationOnScreen(location)
        }

        override fun onDraw(canvas: Canvas) {
            canvas.drawPath(path, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x: Float = event.x
            val y: Float = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> {
                    if (!flag) {
                        time = SystemClock.elapsedRealtime()
                        flag = true
                    }
                    path.moveTo(x, y)
                }
                MotionEvent.ACTION_MOVE -> {
                    val newTime = TextView(context)
                    newTime.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.toFloat())
                    newTime.text = getTimeOut()
                    timerLayout.addView(newTime)

                    val newPath = TextView(context)
                    newPath.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30.toFloat())
                    newPath.text = getPathString(x, y)
                    timerLayout.addView(newPath)

                    path.lineTo(x, y)
                }
            }

            invalidate()
            return true
        }

        private fun getTimeOut(): String {
            val outTime: Long = SystemClock.elapsedRealtime() - time
            count++
            return String.format("%d\t%02d:%02d:%02d", count, outTime / 1000 / 60, (outTime / 1000) % 60, (outTime % 1000) / 10)
        }

        private fun getPathString(x: Float, y: Float): String {
            return String.format("x: %d, y: %d", x.toInt() - location[0], y.toInt() - location[1])
        }
    }
}