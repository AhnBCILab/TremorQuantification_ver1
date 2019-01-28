package com.ahnbcilab.tremorquantification.tremorquantification


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_survey_main.*

class SurveyMainActivity : AppCompatActivity() {
    private val patientId: Int by lazy { intent.extras.getInt("patientId") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey_main)
        val fragmentAdapter = PageAdapter(supportFragmentManager)
        viewpager_main.adapter = fragmentAdapter
        tabs_main.setupWithViewPager(viewpager_main)
        val kotlinFragment = ThridTask.newInstance(patientId)

        submittBtn.setOnClickListener {
            startActivity(Intent(this, SubmitActivity::class.java))
        }
//        if (intent.hasExtra("nameKey")) {
//            textView.text = intent.getStringExtra("nameKey")
//        }
    }

}
