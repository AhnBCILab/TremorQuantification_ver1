package com.ahnbcilab.tremorquantification.tremorquantification

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat.startActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import com.ahnbcilab.tremorquantification.controller.DataController
import com.ahnbcilab.tremorquantification.data.PatientData
import kotlinx.android.synthetic.main.activity_spiral_test_list.*
import kotlinx.android.synthetic.main.add_patient_dialog.*

class SpiralTestListActivity : AppCompatActivity() {

    companion object {
        lateinit var adapter: PatientAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spiral_test_list)

        SpiralTestListActivity.adapter = PatientAdapter(this, DataController.getDataList())
        patientList.adapter = SpiralTestListActivity.adapter
        patientList.emptyView = empty

        addBtn.setOnClickListener {
            val dialog = CustomAddDialog(this)
            dialog.show()
        }
    }
}

class PatientAdapter(context: Context, val listItem: ArrayList<PatientData>) : BaseAdapter() {
    private val mInflator: LayoutInflater = LayoutInflater.from(context)

    private inner class ViewHolder {
        lateinit var name: TextView
        lateinit var description: TextView
    }

    fun add(data: PatientData) {
        DataController.addData(data)
    }

    override fun getCount(): Int {
        return this.listItem.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItem(position: Int): Any {
        return this.listItem[position]
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = mInflator.inflate(R.layout.patient_list_item, parent, false)

            holder = ViewHolder()
            holder.name = view.findViewById(R.id.patientName) as TextView
            holder.description = view.findViewById(R.id.patientDescription) as TextView
            view.tag = holder
        }
        else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val patient = getItem(position) as PatientData
        val name = holder.name
        val description = holder.description

        name.text = patient.name
        description.text = patient.description

        return view
    }
}

private class CustomAddDialog(context: Context) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        setContentView(R.layout.add_patient_dialog)

        addDialogCancel.setOnClickListener {
            dismiss()
        }

        addDialogAdd.setOnClickListener {
            when {
                addDialogName.text.isBlank() -> addDialogNameLayout.error = "Enter the name"
                addDialogBirth.text.isBlank() -> addDialogBirthLayout.error = "Enter the birth"
                else -> {
                    val newData = PatientData(addDialogName.text.toString(), addDialogBirth.text.toString().toInt())
                    if (addDialogDescription.text.isNotBlank())
                        newData.description = addDialogDescription.text.toString()

                    SpiralTestListActivity.adapter.add(newData)
                    dismiss()

                    val intent = Intent(context, SpiralTestActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                    startActivity(context, intent, null)
                }
            }
        }

        addDialogName.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (!hasFocus && !addDialogName.text.isBlank())
                    addDialogName.error = null
            }
        }

        addDialogBirth.setOnFocusChangeListener { _, hasFocus ->
            run {
                if (!hasFocus && !addDialogBirth.text.isBlank())
                    addDialogName.error = null
            }
        }
    }
}