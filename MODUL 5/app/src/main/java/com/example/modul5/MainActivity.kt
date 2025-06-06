package com.example.modul5

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.modul5.data.api.RetrofitInstance
import com.example.modul5.data.local.PostDatabase
import com.example.modul5.data.models.Post
import com.example.modul5.data.repository.PostRepository
import com.example.modul5.viewModel.PostViewModel

class MainActivity : ComponentActivity() {

    class PostViewModelFactory(
        private val repository: PostRepository
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return PostViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val api = RetrofitInstance.api
        val dao = PostDatabase.getDatabase(applicationContext).postDao()
        val repository = PostRepository(api, dao)

        val factory = PostViewModelFactory(repository)

        setContent {
            val postViewModel: PostViewModel = viewModel(factory = factory)

            val posts by postViewModel.posts.collectAsState()
            val favoritePosts by postViewModel.favorites.collectAsState()
            val errorMessage by postViewModel.errorMessage.collectAsState()

            val navController = rememberNavController()
            var selectedTab by remember { mutableStateOf("list") }

            Scaffold(
                topBar = {
                    TopAppBar(title = { Text("Daftar Doa") })
                },
                bottomBar = {
                    NavigationBar {
                        NavigationBarItem(
                            selected = selectedTab == "list",
                            onClick = { selectedTab = "list" },
                            icon = { Icon(painterResource(id = android.R.drawable.ic_menu_agenda), contentDescription = "List") },
                            label = { Text("List") }
                        )
                        NavigationBarItem(
                            selected = selectedTab == "favorit",
                            onClick = { selectedTab = "favorit" },
                            icon = { Icon(painterResource(id = android.R.drawable.star_on), contentDescription = "Favorit") },
                            label = { Text("Favorit") }
                        )
                    }
                }
            ) { paddingValues ->

                val context = LocalContext.current

                val onAddFavoriteWithToast: (Post) -> Unit = { post ->
                    postViewModel.addToFavorite(post)
                    Toast.makeText(context, "Added to favorites", Toast.LENGTH_SHORT).show()
                }
                val onRemoveFavoriteWithToast: (Post) -> Unit = { post ->
                    postViewModel.removeFromFavorite(post)
                    Toast.makeText(context, "Removed from favorites", Toast.LENGTH_SHORT).show()
                }

                when (selectedTab) {
                    "list" -> {
                        if (!errorMessage.isNullOrEmpty()) {
                            Text(
                                text = errorMessage ?: "",
                                modifier = Modifier
                                    .padding(16.dp)
                                    .padding(paddingValues),
                                color = MaterialTheme.colorScheme.error
                            )
                        } else {
                            DoaList(
                                posts = posts,
                                navController = navController,
                                onAddFavorite = onAddFavoriteWithToast,
                                modifier = Modifier.padding(paddingValues)
                            )
                        }
                    }
                    "favorit" -> {
                        FavoritScreen(
                            favorites = favoritePosts,
                            onRemoveFavorite = onRemoveFavoriteWithToast,
                            modifier = Modifier.padding(paddingValues)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DoaList(
    posts: List<Post>,
    navController: NavHostController,
    onAddFavorite: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier
    ) {
        items(posts) { post ->
            DoaItem(
                post = post,
                navController = navController,
                onAddFavorite = onAddFavorite
            )
        }
    }
}

@Composable
fun FavoritScreen(
    favorites: List<Post>,
    onRemoveFavorite: (Post) -> Unit,
    modifier: Modifier = Modifier
) {
    if (favorites.isEmpty()) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "Halaman Favorit masih kosong", style = MaterialTheme.typography.titleMedium)
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = modifier
        ) {
            items(favorites) { post ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(6.dp)
                ) {
                    Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                        post.imageResId?.let { resId ->
                            Image(
                                painter = painterResource(id = resId),
                                contentDescription = "Gambar Doa",
                                modifier = Modifier
                                    .size(80.dp)
                                    .padding(end = 16.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(text = post.doa, style = MaterialTheme.typography.titleMedium)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = post.artinya, style = MaterialTheme.typography.bodyMedium)
                        }
                        Button(
                            onClick = { onRemoveFavorite(post) },
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("Hapus")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DoaItem(
    post: Post,
    navController: NavHostController,
    onAddFavorite: (Post) -> Unit,
) {
    val context = LocalContext.current

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            post.imageResId?.let { resId ->
                Image(
                    painter = painterResource(id = resId),
                    contentDescription = "Gambar Doa",
                    modifier = Modifier
                        .size(80.dp)
                        .padding(end = 16.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = post.doa, style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            navController.navigate(
                                "detail/${post.doa}/${post.ayat}/${post.latin}/${post.artinya}/${post.imageResId ?: 0}"
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Detail", style = MaterialTheme.typography.bodyMedium)
                    }

                    Button(
                        onClick = {
                            val youtubeUrl = "https://www.youtube.com/watch?v=G5O_TpkKibg"
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                            intent.setPackage("com.google.android.youtube")

                            try {
                                context.startActivity(intent)
                            } catch (e: Exception) {
                                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(youtubeUrl))
                                context.startActivity(browserIntent)
                            }
                        },
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("App")
                    }

                    Button(
                        onClick = { onAddFavorite(post) },
                        modifier = Modifier
                            .weight(1f)
                            .height(36.dp),
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text("Favorit")
                    }
                }
            }
        }
    }
}
