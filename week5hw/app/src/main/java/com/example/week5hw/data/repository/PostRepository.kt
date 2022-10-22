package com.example.week5hw.data.repository

import com.example.week5hw.data.local.database.entity.PostEntity
import com.example.week5hw.ui.model.post.Post
import retrofit2.Call

interface PostRepository {
    fun getPosts(): Call<List<Post>>
    fun getPostById(id: Int): PostEntity?
    fun insertFavoritePost(post: PostEntity)
    fun deleteFavoritePost(id: String)
    fun getFavorites(): List<PostEntity>
}