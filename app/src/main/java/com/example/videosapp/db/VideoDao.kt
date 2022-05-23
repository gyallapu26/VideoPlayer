package com.example.videosapp.db

import androidx.room.*
import com.example.videosapp.ui.Video
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<Video>)

    @Query("SELECT * from Video")
     fun getAllVideos(): Flow<List<Video>>

    @Query("SELECT * from Video where isWatched= 1")
    fun getHistory(): Flow<List<Video>>

    @Update
    suspend fun updateHistory(video: Video)
}