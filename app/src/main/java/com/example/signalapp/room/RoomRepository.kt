package com.example.signalapp.room

import androidx.lifecycle.LiveData

class RoomRepository(private val dao: DaoBooks) : Repository {
    override val readAllNote: LiveData<List<Books>>
        get() = dao.getAllNotes()

    override suspend fun create(books: Books) {
        dao.insert(books)
    }

    override suspend fun delete(books: Books) {
        dao.delete(books)
    }

    fun isBookFavorite(id: Int): Boolean {
        return dao.isBookFavorite(id)
    }
}