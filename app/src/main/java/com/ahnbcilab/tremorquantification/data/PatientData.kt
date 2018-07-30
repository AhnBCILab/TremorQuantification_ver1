package com.ahnbcilab.tremorquantification.data

import java.util.*

data class PatientData(var name: String, var birth: Int) {

    // When connect with database, ID property will be here.
    // val ID = db.getID()

    val age: Int = Calendar.getInstance().get(Calendar.YEAR) - birth + 1
    var description: String = ""
}