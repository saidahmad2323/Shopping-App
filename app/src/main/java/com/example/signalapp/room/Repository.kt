package com.example.signalapp.room

import androidx.lifecycle.LiveData

interface Repository {
    val readAllNote: LiveData<List<Books>>
    suspend fun create(books: Books)

    suspend fun delete(books: Books)
}
