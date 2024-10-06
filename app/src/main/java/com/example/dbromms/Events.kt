package com.example.dbromms

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "evnt_table")
data class Events(
    //id, name, description
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val name : String,
    val description : String,
    var isSynced : Boolean = false,
    var firestoreId : String? = null

)
