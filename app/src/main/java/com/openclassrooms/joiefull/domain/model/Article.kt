package com.openclassrooms.joiefull.domain.model

data class Article(
    val id: Int,
    val imageUrl: String,
    val imageDescription: String,
    val name: String,
    val category: Category,
    val likes: Int,
    val price: Double,
    val originalPrice: Double,
)

enum class Category {
    TOPS, BOTTOMS, SHOES, ACCESSORIES, UNKNOWN
}
