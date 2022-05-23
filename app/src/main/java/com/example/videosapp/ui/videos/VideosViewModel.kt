package com.example.videosapp.ui.videos

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videosapp.db.VideoDbQueryUseCase
import com.example.videosapp.ui.Video
import com.example.videosapp.ui.VideosRepository
import com.example.videosapp.ui.util.Result
import com.example.videosapp.ui.util.asFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.Error
import java.lang.Exception
import javax.inject.Inject


@HiltViewModel
class VideosViewModel @Inject constructor(
    private val videoDbQueryUseCase: VideoDbQueryUseCase,
    private val videosRepository: VideosRepository,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {

    val videosResult: MutableStateFlow<Result<List<Video>?>> = MutableStateFlow(Result.InProgress)

    init {
        fetchVideos()
    }

     fun fetchVideos() {
        fetchVideosFromDb()
        updateDb()
    }

    private fun updateDb() {
        viewModelScope.launch(ioDispatcher) {
            asFlow(ioDispatcher) {
                videosRepository.fetchVideos()
            }.collect {
                /**
                 * Once new data received from remote, the room been updated
                 * */
                if (it is Result.Success) {
                    it.data?.let { videos ->
                        videoDbQueryUseCase.insertAllVideos(videos)
                    }
                }
            }
        }
    }


    private fun fetchVideosFromDb() {
        viewModelScope.launch(ioDispatcher) {
            videoDbQueryUseCase.getAllVideos().collect {
                /**
                 *  DB acts as single source of truth to the client now
                 * */
               try {
                   videosResult.emit(Result.Success(it))
               } catch (e: Exception) {
                   videosResult.emit(Result.Error(e.message ?: "Something went wrong while reading from db"))
               }
            }
        }
    }

    fun updateVideoStatus(video: Video) {
       viewModelScope.launch(ioDispatcher) {
           videoDbQueryUseCase.updateVideo(video.apply { isWatched = true })
       }
    }
}
