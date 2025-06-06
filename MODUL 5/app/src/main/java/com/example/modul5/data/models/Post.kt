package com.example.modul5.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class Post(
    @PrimaryKey val id: String,
    val doa: String,
    val ayat: String,
    val latin: String,
    val artinya: String,
    val imageResId: Int? = null
)
