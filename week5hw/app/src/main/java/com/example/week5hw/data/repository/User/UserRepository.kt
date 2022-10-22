package com.example.week5hw.data.repository.User

import com.example.week5hw.ui.model.users.Users
import retrofit2.Call

interface UserRepository {
    fun getUsers(): Call<List<Users>>
}