package com.example.roomdemo.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Subscriber")
data class Subscriber(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Id")
    var id: Int,

    @ColumnInfo(name = "name")
    var name: String,

    @ColumnInfo(name = "email")
    var email: String
)