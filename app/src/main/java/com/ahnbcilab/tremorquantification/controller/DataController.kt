package com.ahnbcilab.tremorquantification.controller

import com.ahnbcilab.tremorquantification.data.PatientData

class DataController {
    companion object {
        val array = ArrayList<PatientData>()

        fun getData(id: Int): PatientData {
            if (id >= array.size || id < 0)
                throw ArrayIndexOutOfBoundsException("Id should be larger or equal than 0 and less then " + array.size)

            return array[id]
        }

        fun getDataList(): ArrayList<PatientData> {
            return array
        }

        fun setData(id: Int, data: PatientData): Boolean {
            try {
                array[id] = data
            }
            catch (e: Exception) {
                throw e
            }
            return true
        }

        fun addData(data: PatientData): Boolean {
            try {
                array.add(data)
            }
            catch (e: Exception) {
                throw e
            }

            return true
        }
    }
}