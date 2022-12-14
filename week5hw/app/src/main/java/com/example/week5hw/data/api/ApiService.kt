package com.example.week5hw.data.api
import com.example.week5hw.ui.model.post.Post
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @DELETE("posts/{id}")
    fun deletePost(@Path("{id}") id:String) : Call<Post>
}

