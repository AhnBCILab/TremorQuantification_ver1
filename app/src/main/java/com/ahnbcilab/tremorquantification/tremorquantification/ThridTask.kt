package com.ahnbcilab.tremorquantification.tremorquantification

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.IgnoreExtraProperties

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
        val ID = arguments!!.getInt("ID")
        val mDatabase = FirebaseDatabase.getInstance().getReference();

        */
        class ThridTask : Fragment() {
            // private lateinit var adapter: PatientAdapter
            private val mDatabase: DatabaseReference? = null

            override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
                // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_thrid_task, container, false)

    }


    companion object {

        fun newInstance(patient: Int): ThridTask {

            val args = Bundle()
            args.putInt("ID", patient)
            val fragment = ThridTask()
            fragment.arguments = args
            return fragment
        }
    }

    @IgnoreExtraProperties
    inner class User {

        lateinit var username: String
        lateinit var sex: String
        lateinit var desc: String
        var age: Int = 0


        constructor() {
            // Default constructor required for calls to DataSnapshot.getValue(User.class)
        }

        constructor(username: String, sex: String, desc: String, age:Int) {
            this.username = username
            this.sex = sex
            this.desc = desc
            this.age = age
        }
    }

}
