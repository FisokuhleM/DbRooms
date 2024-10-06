package com.example.dbromms

import kotlinx.coroutines.flow.Flow


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EventDao {
    //Crud ---> Create and Insert, Read from it

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun  insert(events: Events)

    @Query("SELECT * FROM evnt_table")
    fun getAllEvents() : Flow<List<Events>>

    //Tracks unsynced events
    @Query("SELECT * FROM evnt_table WHERE isSynced = 0")
    //WHAT IS A SUSPENSION FUNCTION???
    suspend fun getUnsyncedEvents() : List<Events>

}