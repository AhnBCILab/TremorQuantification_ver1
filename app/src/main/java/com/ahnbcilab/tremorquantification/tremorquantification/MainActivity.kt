package com.ahnbcilab.tremorquantification.tremorquantification

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spiralTestBtn.setOnClickListener {
            startActivity(Intent(this, SpiralTestListActivity::class.java))
        }

        patientListBtn.setOnClickListener {
            startActivity(Intent(this, PatientListActivity::class.java))
        }
    }
}
