package com.example.modul5.data.api

import com.example.modul5.data.models.Post
import retrofit2.http.GET

interface ApiServices {
    @GET("api")
    suspend fun getDoaList(): List<Post>
}
