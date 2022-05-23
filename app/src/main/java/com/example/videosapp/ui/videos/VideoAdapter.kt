package com.example.videosapp.ui.videos

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.videosapp.databinding.ItemVideoBinding
import com.example.videosapp.ui.Video
import com.squareup.picasso.Picasso

class VideoAdapter(
    private var videos: List<Video>,
    private val onclick: (Video) -> Unit
) : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            ItemVideoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = videos[position]
        holder.descriptionTv
        holder.titleTv.text = item.title
        holder.descriptionTv.text = item.description

        Picasso.with(holder.itemView.context).load(item.thumbnail).fit().centerCrop()
            // .placeholder(R.drawable.user_placeholder)
            // .error(R.drawable.user_placeholder_error)
            .into(holder.videoTv)
        holder.itemView.setOnClickListener {
            onclick(videos[position])
        }

    }

    override fun getItemCount(): Int = videos.size

    inner class ViewHolder(binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val titleTv: TextView = binding.titleTv
        val descriptionTv: TextView = binding.descriptionTv
        val videoTv: ImageView = binding.videoIv

    }
    }