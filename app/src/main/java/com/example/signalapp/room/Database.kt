package com.example.signalapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Books::class],
    version = 1
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun dao(): DaoBooks

    companion object {
        @Volatile
        private var INSTANCE: AppRoomDatabase? = null

        fun getInstance(context: Context): AppRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppRoomDatabase::class.java,
                    "database_room"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
