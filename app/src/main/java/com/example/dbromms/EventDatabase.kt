package com.example.dbromms

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database (entities = [Events::class], version = 2, exportSchema =false)
abstract class EventDatabase : RoomDatabase()
{
    abstract fun eventDao(): EventDao

    //Uses a companion object with the db

    companion object{
        @Volatile
        private var INSTANCE : EventDatabase? = null
        fun getDatabase(context: Context) : EventDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventDatabase::class.java,
                    "evnt_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}