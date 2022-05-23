package com.example.videosapp.ui.videos

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.videosapp.databinding.FragmentVideoListBinding
import com.example.videosapp.ui.Video
import com.example.videosapp.ui.util.gone
import com.example.videosapp.ui.util.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class VideoListFragment : Fragment() {

    private var _binding: FragmentVideoListBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideosViewModel by viewModels()
    private var adapter: VideoAdapter? = null
    private var videoPlayerObserver: VideoPlayerObserver? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentVideoListBinding.inflate(inflater, container, false)
        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClickListener()
        videoPlayerObserver = VideoPlayerObserver(context = requireContext(), binding = binding )
        videoPlayerObserver?.let { viewLifecycleOwner.lifecycle.addObserver(it) }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewModel.videosResult.collect {
                when (it) {
                    is com.example.videosapp.ui.util.Result.InProgress -> {
                        binding.progressBar.visible()
                        binding.errorLayout.root.gone()
                    }
                    is com.example.videosapp.ui.util.Result.Error -> {
                        binding.progressBar.gone()
                        binding.errorLayout.root.visible()
                    }
                    is com.example.videosapp.ui.util.Result.Success -> {
                        binding.progressBar.gone()
                        binding.errorLayout.root.gone()
                        it.data?.let { news ->
                            initAdapter(news)
                        }

                    }
                }

            }
        }
    }

    private fun initClickListener() {
        binding.errorLayout.retryButton.setOnClickListener {
            viewModel.fetchVideos()
        }
    }

    private fun initAdapter(videos: List<Video>) {
        adapter = VideoAdapter(videos) {
            binding.videoView.visible()
            videoPlayerObserver?.initializePlayer(it)
            viewModel.updateVideoStatus(it)
        }
        binding.list.adapter = adapter
    }
}