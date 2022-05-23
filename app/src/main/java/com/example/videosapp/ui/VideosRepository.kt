package com.example.videosapp.ui

import com.example.videosapp.ui.api.ApiService
import javax.inject.Inject

class VideosRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun fetchVideos() = apiService.fetchVideos()
}