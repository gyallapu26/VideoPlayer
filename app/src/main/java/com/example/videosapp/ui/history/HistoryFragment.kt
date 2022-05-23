package com.example.videosapp.ui.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.videosapp.databinding.FragmentHistoryBinding
import com.example.videosapp.ui.Video
import com.example.videosapp.ui.util.Result
import com.example.videosapp.ui.util.visible
import com.example.videosapp.ui.videos.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val viewmodel: HistoryViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var adapter: VideoAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    @SuppressLint("ShowToast")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            viewmodel.result.collect {
              when(it) {
                  is Result.Success -> {
                      if (it.data.isEmpty()) {
                          /// TODO show static xml view and also prefer string.xml
                          Toast.makeText(requireContext(), "No history", Toast.LENGTH_LONG)
                      }
                      initAdapter(it.data)
                  }
                  is Result.Error -> {
                      Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_LONG)
                  }
              }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initAdapter(videos: List<Video>) {
        adapter = VideoAdapter(videos) {
        }
        binding.list.adapter = adapter
    }
}