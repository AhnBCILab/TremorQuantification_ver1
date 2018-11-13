package com.ahnbcilab.tremorquantification.tremorquantification

import com.google.firebase.database.DataSnapshot

class Patient(snapshot: DataSnapshot){
    lateinit var id: String
    lateinit var name: String
    lateinit var description: String
    //lateinit var age: String


    init{
        try{
            val data: HashMap<String, Any> = snapshot.value as HashMap<String, Any>
            id = snapshot.key ?: ""
            name = data["name"] as String
            description = data["description"] as String
            //age = data["age"] as String
        } catch(e: Exception){
            e.printStackTrace()
        }
    }
}