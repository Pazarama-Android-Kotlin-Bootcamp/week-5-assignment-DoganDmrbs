package com.example.week5hw.ui.posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.week5hw.databinding.FragmentPostBinding
import com.example.week5hw.ui.loadingprocess.LoadingProgressBar
import com.example.week5hw.ui.model.DataState
import com.example.week5hw.ui.model.post.PostDTO
import com.example.week5hw.ui.posts.adapter.OnPostClickListener
import com.example.week5hw.ui.posts.adapter.PostsAdapter
import com.example.week5hw.ui.posts.viewmodel.PostViewEvent
import com.example.week5hw.ui.posts.viewmodel.PostsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostsFragment : Fragment(), OnPostClickListener {
    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentPostBinding
    private val viewModel by viewModels<PostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPostBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = LoadingProgressBar(requireContext())

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.postLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData->
                        binding.rvPostsList.adapter = PostsAdapter(this).apply {
                            submitList(safeData)
                        }
                    } ?: run {
                        Toast.makeText(requireContext(),"No data", Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(binding.root,it.message,Snackbar.LENGTH_LONG).show()

                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }
        }

        viewModel.eventStateLiveData.observe(viewLifecycleOwner){
            when (it) {
                is PostViewEvent.ShowMessage -> {}
                is PostViewEvent.NavigateToDetail -> {}
            }
        }
    }

    override fun onPostClick(post: PostDTO) {
        viewModel.onFavoritePost(post)
    }


}