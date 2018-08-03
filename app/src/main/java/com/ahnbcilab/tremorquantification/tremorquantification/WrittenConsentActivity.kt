package com.ahnbcilab.tremorquantification.tremorquantification

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import com.ahnbcilab.tremorquantification.functions.Drawable
import kotlinx.android.synthetic.main.activity_written_consent.*
import java.text.SimpleDateFormat
import java.util.*

class WrittenConsentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_written_consent)

        val drawView = Draw(this)
        signature.addView(drawView)

        val current: String = SimpleDateFormat("yyyy년  MM월  dd일").format(Date())
        currentDate.text = current
        
        agree1Group.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.agree1 -> {
                    if (agree2.isChecked) finalAgree.visibility = View.VISIBLE
                    else finalAgree.visibility = View.GONE
                }
                R.id.disagree1 -> {
                    finalAgree.visibility = View.GONE
                    drawView.clear()
                    btnDeactivate(this)
                }
            }
        }

        agree2Group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.agree2 -> {
                    if (agree1.isChecked) finalAgree.visibility = View.VISIBLE
                    else finalAgree.visibility = View.GONE
                }
                R.id.disagree2 -> {
                    finalAgree.visibility = View.GONE
                    drawView.clear()
                    btnDeactivate(this)
                }
            }
        }

        goToTest.setOnClickListener {
            // Save patient's signature

            val intent = Intent(this, SpiralTestActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
            ContextCompat.startActivity(this, intent, null)
        }
    }

    inner class Draw(context: Context) : Drawable(context) {
        override fun onTouchEvent(event: MotionEvent): Boolean {
            val x: Float = event.x
            val y: Float = event.y

            when(event.action) {
                MotionEvent.ACTION_DOWN -> path.moveTo(x, y)
                MotionEvent.ACTION_MOVE -> path.lineTo(x, y)
                MotionEvent.ACTION_UP -> btnActive(context)
            }

            invalidate()
            return true
        }
    }

    private fun btnActive(context: Context) {
        goToTest.isEnabled = true
        goToTest.setBackgroundColor(ContextCompat.getColor(context, R.color.Primary))
        goToTest.setTextColor(Color.WHITE)
    }

    private fun btnDeactivate(context: Context) {
        goToTest.isEnabled = false
        goToTest.setBackgroundColor(ContextCompat.getColor(context, R.color.WhiteGray))
        goToTest.setTextColor(Color.DKGRAY)
    }
}