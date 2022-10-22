package com.example.week5hw.ui.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.week5hw.databinding.FragmentUsersBinding
import com.example.week5hw.ui.loadingprocess.LoadingProgressBar
import com.example.week5hw.ui.model.DataState
import com.example.week5hw.ui.users.adapter.UsersAdapter
import com.example.week5hw.ui.users.viewmodel.UserViewEvent
import com.example.week5hw.ui.users.viewmodel.UsersViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserFragment : Fragment() {
    lateinit var loadingProgressBar: LoadingProgressBar
    private lateinit var binding: FragmentUsersBinding
    private val viewModel by viewModels<UsersViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentUsersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadingProgressBar = LoadingProgressBar(requireContext())

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.userLiveData.observe(viewLifecycleOwner){
            when(it){
                is DataState.Success -> {
                    loadingProgressBar.hide()
                    it.data?.let { safeData->
                        binding.rvUsersList.adapter = UsersAdapter().apply {
                            submitList(safeData)
                        }
                    } ?: run {
                        Toast.makeText(requireContext(),"No data", Toast.LENGTH_SHORT).show()
                    }
                }
                is DataState.Error -> {
                    loadingProgressBar.hide()
                    Snackbar.make(binding.root,it.message, Snackbar.LENGTH_LONG).show()

                }
                is DataState.Loading -> {
                    loadingProgressBar.show()
                }
            }
        }

        viewModel.eventStateLiveData.observe(viewLifecycleOwner){
            when (it) {
                is UserViewEvent.ShowMessage -> {}
                is UserViewEvent.NavigateToDetail -> {}
            }
        }
    }
}