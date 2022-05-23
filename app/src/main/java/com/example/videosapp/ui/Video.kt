package com.example.videosapp.ui

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity data class Video(
    @PrimaryKey
    val id: Long,
    val title: String,
    val description: String,
    val media_url: String,
    val thumbnail: String
) {
    var isWatched: Boolean = false
}