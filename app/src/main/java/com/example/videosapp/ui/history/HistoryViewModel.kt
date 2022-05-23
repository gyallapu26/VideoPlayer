package com.example.videosapp.ui.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.videosapp.db.VideoDbQueryUseCase
import com.example.videosapp.ui.Video
import com.example.videosapp.ui.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(private val videoDbQueryUseCase: VideoDbQueryUseCase,
                       private val ioDispatcher: CoroutineDispatcher) : ViewModel() {

    val result: MutableStateFlow<Result<List<Video>>> = MutableStateFlow(Result.InProgress)

  init {
      fetchHistory()
  }

     private fun fetchHistory() {
        viewModelScope.launch(ioDispatcher) {
            videoDbQueryUseCase.getHistory().collect {
                result.emit(Result.Success(it))
            }
        }
    }
}