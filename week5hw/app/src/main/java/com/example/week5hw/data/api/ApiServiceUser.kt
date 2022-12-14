package com.example.week5hw.data.api

import com.example.week5hw.ui.model.users.Users
import retrofit2.Call
import retrofit2.http.GET

interface ApiServiceUser {
    @GET("users")
    fun getUsers(): Call<List<Users>>
}