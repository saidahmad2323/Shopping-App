package com.example.signalapp.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DaoBooks {
    @Query("SELECT * FROM table_name")
    fun getAllNotes(): LiveData<List<Books>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(books: Books)

    @Delete
    fun delete(books: Books)

    @Query("SELECT EXISTS(SELECT 1 FROM table_name WHERE id = :id)")
    fun isBookFavorite(id: Int): Boolean
}