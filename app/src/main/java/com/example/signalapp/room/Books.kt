package com.example.signalapp.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_name")
data class Books(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "hits") val hits: String,
    @ColumnInfo(name = "acsi") val acsi: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "imageUri") val imageUri: String
)


