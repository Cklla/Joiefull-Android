package com.openclassrooms.joiefull.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

// Reflète exactement la forme du JSON renvoyé par l'API
@JsonClass(generateAdapter = true)
data class ArticleDto(
    val id: Int,
    val picture: PictureDto,
    val name: String,
    val category: String,
    val likes: Int,
    val price: Double,
    @Json(name = "original_price") val originalPrice: Double
)

@JsonClass(generateAdapter = true)
data class PictureDto(
    val url: String,
    val description: String
)
