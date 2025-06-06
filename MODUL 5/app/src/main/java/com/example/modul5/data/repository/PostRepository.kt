package com.example.modul5.data.repository

import com.example.modul5.data.api.ApiServices
import com.example.modul5.data.local.PostDao
import com.example.modul5.data.models.Post
import kotlinx.coroutines.flow.Flow

class PostRepository(
    private val api: ApiServices,
    private val dao: PostDao
) {
    fun getPosts(): Flow<List<Post>> = dao.getAllPosts()

    suspend fun fetchAndCachePosts() {
        try {
            val response = api.getDoaList()
            dao.clearPosts()
            dao.insertPosts(response)
        } catch (e: Exception) {
        }
    }
}
