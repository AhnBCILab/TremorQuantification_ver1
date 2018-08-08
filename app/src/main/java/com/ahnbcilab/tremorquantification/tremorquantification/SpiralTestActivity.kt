package com.ahnbcilab.tremorquantification.tremorquantification

import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Canvas
import android.os.SystemClock
import android.util.TypedValue
import android.view.MotionEvent
import android.widget.TextView
import android.widget.Toast
import com.ahnbcilab.tremorquantification.functions.Drawable
import kotlinx.android.synthetic.main.activity_spiral_test.*

class SpiralTestActivity : AppCompatActivity() {

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
            val intent = Intent(this, SpiralTestListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }

        nextBtn.setOnClickListener {
            Toast.makeText(this, "Not implemented", Toast.LENGTH_LONG).show()
        }
    }

    inner class MyView(context: Context) : Drawable(context) {
        private var flag: Boolean = false
        private var time: Long = 0

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
                    path.lineTo(x, y)
                }
            }

            invalidate()
            return true
        }
    }
}