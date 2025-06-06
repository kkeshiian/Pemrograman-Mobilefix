package com.example.modul5.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DetailScreen(doa: String, ayat: String, latin: String, artinya: String, imageResId: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Gambar Detail",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Judul:", style = MaterialTheme.typography.titleMedium)
        Text(text = doa, fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))

        Text(text = "Ayat:", style = MaterialTheme.typography.titleMedium)
        Text(text = ayat, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))

        Text(text = "Latin:", style = MaterialTheme.typography.titleMedium)
        Text(text = latin, fontSize = 18.sp, modifier = Modifier.padding(bottom = 8.dp))

        Text(text = "Artinya:", style = MaterialTheme.typography.titleMedium)
        Text(text = artinya, fontSize = 18.sp)
    }
}
