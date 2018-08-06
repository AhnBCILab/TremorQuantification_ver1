package com.ahnbcilab.tremorquantification.data

import java.util.*

data class PatientData(var name: String, var birth: Int, var description: String?) {
    var id: Int = -1
    val age: Int = Calendar.getInstance().get(Calendar.YEAR) - birth + 1
}