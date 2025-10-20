package com.example.lvlup.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class GamerNews(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String,
    val imageUrl: String // Puedes usar imagenes de internet o de tus drawables locales si configuras painterResource
)