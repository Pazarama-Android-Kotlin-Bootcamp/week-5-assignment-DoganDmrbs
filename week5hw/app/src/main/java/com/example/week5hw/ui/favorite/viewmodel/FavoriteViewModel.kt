package com.example.week5hw.ui.favorite.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.week5hw.data.repository.PostRepository
import com.example.week5hw.ui.model.post.PostDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val postRepository: PostRepository) :
    ViewModel() {
    private var _postLiveData = MutableLiveData<List<PostDTO>?>()
    val postLiveData: LiveData<List<PostDTO>?>
        get() = _postLiveData

    private val _eventStateLiveData = MutableLiveData<FavoritesViewEvent>()
    val eventStateLiveData: LiveData<FavoritesViewEvent>
        get() = _eventStateLiveData


     fun getPosts() {
        postRepository.getFavorites()
        _postLiveData.postValue(postRepository.getFavorites().map {
            PostDTO(it.postBody, it.id?.toInt(), it.postId?.toInt(), it.postTitle, true)
        }
        )


    }
    fun onFavoritePost(post: PostDTO) {
        postRepository.deleteFavoritePost(post.id.toString())
    }
}


sealed class FavoritesViewEvent {
    object NavigateToDetail : FavoritesViewEvent()
    class ShowMessage(val message: String?) : FavoritesViewEvent()
}