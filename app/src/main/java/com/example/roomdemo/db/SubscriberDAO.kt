package com.example.roomdemo.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SubscriberDAO {
    @Insert
    suspend fun insertSubscriber(subscriber: Subscriber): Long

    @Update
    suspend fun updateSubscriber(subscriber: Subscriber)

    @Delete
    suspend fun deleteSubscriber(subscriber: Subscriber)

    @Query("Delete from Subscriber")
    suspend fun deleteAll()

    @Query("select * from Subscriber")
    fun getAllSubscriber(): LiveData<List<Subscriber>>
}