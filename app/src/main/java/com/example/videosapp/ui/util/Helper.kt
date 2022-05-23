package com.example.videosapp.ui.util

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}