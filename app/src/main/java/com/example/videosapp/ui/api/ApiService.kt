package com.example.videosapp.ui.api

import com.example.videosapp.ui.Video
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {


    @GET("f35a862c-7e85-4bb7-a80f-2296e87755f4")
   suspend fun fetchVideos(): Response<List<Video>>

}