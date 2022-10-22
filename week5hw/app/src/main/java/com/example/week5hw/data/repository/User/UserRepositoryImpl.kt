package com.example.week5hw.data.repository

import com.example.week5hw.data.api.ApiServiceUser
import com.example.week5hw.data.repository.User.UserRepository
import com.example.week5hw.ui.model.users.Users
import retrofit2.Call

class UserRepositoryImpl constructor(
    private val apiServiceUser: ApiServiceUser
) : UserRepository {
    override fun getUsers(): Call<List<Users>> {
        return apiServiceUser.getUsers()
    }
}