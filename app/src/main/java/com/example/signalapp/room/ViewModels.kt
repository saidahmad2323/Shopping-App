package com.example.signalapp.room

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ViewModels(application: Application) : AndroidViewModel(application) {
    private var REPOSITORYS: RoomRepository

    init {
        val dao = AppRoomDatabase.getInstance(application).dao()
        REPOSITORYS = RoomRepository(dao)
    }

    fun readNote() = REPOSITORYS.readAllNote

    fun addNote(books: Books) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORYS.create(books)
        }
    }

    fun delete(books: Books) {
        viewModelScope.launch(Dispatchers.IO) {
            REPOSITORYS.delete(books)
        }
    }
    suspend fun isBookFavorite(id: Int): Boolean {
        return withContext(Dispatchers.IO) {
            REPOSITORYS.isBookFavorite(id)
        }
    }
}

class ViewModelNoteFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ViewModels::class.java)) {
            return ViewModels(application) as T
        }
        throw IllegalArgumentException("throw")
    }
}