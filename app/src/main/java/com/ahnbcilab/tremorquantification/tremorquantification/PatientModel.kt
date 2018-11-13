package com.ahnbcilab.tremorquantification.tremorquantification

import android.util.Log
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


object PatientModel: Observable() {

    private var mValueDataListener: ValueEventListener? = null
    private var mPatientList: ArrayList<Patient>? = ArrayList()

    private fun getDatabaseRef(): DatabaseReference? {
        return FirebaseDatabase.getInstance().reference.child("patient")
    }


    init{
        if(mValueDataListener != null){
            getDatabaseRef()?.removeEventListener(mValueDataListener as ValueEventListener)
        }
        mValueDataListener = null
        Log.i("PatientModel", "data init line 26")

        mValueDataListener = object: ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                try{
                    Log.i("PatientModel", "data updated line 29")
                    val data: ArrayList<Patient> = ArrayList()
                    if(dataSnapshot != null){
                        for (snapshot: DataSnapshot in dataSnapshot.children){
                            try{
                                data.add(Patient(snapshot))
                            } catch(e: Exception){
                                e.printStackTrace()
                            }
                        }
                        mPatientList = data
                        Log.i("PatientModel", "data updated, there are " + mPatientList!!.size + " entrees in the cache")
                        setChanged()
                        notifyObservers()
                    }
                } catch(e: Exception){
                    e.printStackTrace()
                }
            }

            override fun onCancelled(p0: DatabaseError) {
                if(p0 != null){
                    Log.i("PatientModel", "line 51 data update canceled, err = ${p0.message}")
                }
            }
        }
        getDatabaseRef()?.addValueEventListener(mValueDataListener as ValueEventListener)
    }


    fun getData(): ArrayList<Patient>?{
        return mPatientList
    }
}