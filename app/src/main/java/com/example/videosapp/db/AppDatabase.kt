package com.example.videosapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.videosapp.ui.Video

@Database(entities = [Video::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao

}