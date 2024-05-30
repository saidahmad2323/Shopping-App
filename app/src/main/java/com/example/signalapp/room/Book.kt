package com.example.signalapp.room

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID


data class Book(
    val id: Int = 0,
    val name: String = "",
    val price: String = "",
    val hits: String = "",
    val acsi: String = "",
    val description: String = "",
    val imageUri: String = "",
    val category: String = "",
    val createDate: String = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date()),
    var isFavorite: Boolean = false
)
