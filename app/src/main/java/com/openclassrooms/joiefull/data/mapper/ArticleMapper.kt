package com.openclassrooms.joiefull.data.mapper

import com.openclassrooms.joiefull.data.remote.dto.ArticleDto
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.Category

fun ArticleDto.toDomain(): Article {
    return Article(
        id = id,
        imageUrl = picture.url,
        imageDescription = picture.description,
        name = name,
        category = category.toCategory(),
        likes = likes,
        price = price,
        originalPrice = originalPrice,
    )
}

private fun String.toCategory(): Category =
    try {
        Category.valueOf(this)
    } catch (e: IllegalArgumentException) {
        Category.UNKNOWN
    }