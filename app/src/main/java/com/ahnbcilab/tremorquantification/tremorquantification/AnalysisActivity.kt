package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ahnbcilab.tremorquantification.functions.main

class AnalysisActivity : AppCompatActivity() {

    private val filename: String by lazy { intent.extras.getString("filename") }
    private lateinit var thread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        var result: DoubleArray
        val dialog = ProgressDialog(this)
        dialog.setMessage("Analysing...")
        dialog.setCancelable(false);
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", DialogInterface.OnClickListener {dialog, which -> run {
            if (::thread.isInitialized) thread.interrupt()
            dialog.dismiss()
            val cancel_Intent = Intent(this, SpiralTestListActivity::class.java)
            cancel_Intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(cancel_Intent)
        }})
        dialog.show()

        thread = Thread(Runnable {
            result = main.main("${this.filesDir.path}/testData/$filename")
            dialog.dismiss()
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("result", result)
            startActivity(intent)
            finish()
        })
        thread.run()
    }
}
