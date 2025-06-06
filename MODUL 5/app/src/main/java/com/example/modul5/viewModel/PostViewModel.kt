package com.example.modul5.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.modul5.R
import com.example.modul5.data.models.Post
import com.example.modul5.data.repository.PostRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PostViewModel(private val repository: PostRepository) : ViewModel() {

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _favorites = MutableStateFlow<List<Post>>(emptyList())
    val favorites: StateFlow<List<Post>> = _favorites

    private val imageMap = mapOf(
        "1" to R.drawable.satu,
        "2" to R.drawable.dua,
        "3" to R.drawable.tiga,
        "4" to R.drawable.empat,
        "5" to R.drawable.lima,
        "6" to R.drawable.enam,
        "7" to R.drawable.satu,
        "8" to R.drawable.dua,
        "9" to R.drawable.tiga,
        "10" to R.drawable.empat,
        "11" to R.drawable.lima,
        "12" to R.drawable.enam,
        "13" to R.drawable.satu,
        "14" to R.drawable.dua,
        "15" to R.drawable.tiga,
        "16" to R.drawable.empat,
        "17" to R.drawable.lima,
        "18" to R.drawable.enam,
        "19" to R.drawable.satu,
        "20" to R.drawable.dua,
        "21" to R.drawable.tiga,
        "22" to R.drawable.empat,
        "23" to R.drawable.lima,
        "24" to R.drawable.enam,
        "25" to R.drawable.satu,
        "26" to R.drawable.dua,
        "27" to R.drawable.tiga,
        "28" to R.drawable.empat,
        "29" to R.drawable.lima,
        "30" to R.drawable.enam,
        "31" to R.drawable.satu,
        "32" to R.drawable.dua,
        "33" to R.drawable.tiga,
        "34" to R.drawable.empat,
        "35" to R.drawable.lima,
        "36" to R.drawable.enam,
        "37" to R.drawable.satu

    )

    // Ambil data cached dari database, langsung observable
    val posts: StateFlow<List<Post>> = repository.getPosts()
        .map { list ->
            list.map { post ->
                post.copy(
                    imageResId = imageMap[post.id] ?: R.drawable.placeholder
                )
            }
        }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        refreshPosts()
    }

    private fun refreshPosts() {
        viewModelScope.launch {
            try {
                repository.fetchAndCachePosts()
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }

    fun addToFavorite(post: Post) {
        if (!_favorites.value.contains(post)) {
            _favorites.value = _favorites.value + post
        }
    }

    fun removeFromFavorite(post: Post) {
        _favorites.value = _favorites.value - post
    }
}
