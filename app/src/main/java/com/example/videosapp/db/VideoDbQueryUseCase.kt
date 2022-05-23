package com.example.videosapp.db

import com.example.videosapp.ui.Video
import javax.inject.Inject

class VideoDbQueryUseCase @Inject constructor(private val videoDao: VideoDao) {

    suspend fun getHistory() = videoDao.getHistory()

    suspend fun getAllVideos() = videoDao.getAllVideos()

    suspend fun updateVideo(video: Video) = videoDao.updateHistory(video)

    suspend fun insertAllVideos(list: List<Video>) = videoDao.insertVideos(list)

}