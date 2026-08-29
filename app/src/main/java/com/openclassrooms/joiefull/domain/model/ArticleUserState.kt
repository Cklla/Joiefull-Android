package com.openclassrooms.joiefull.domain.model

data class ArticleUserState(
    val isFavorite: Boolean = false,
    val rating: Int = 0,
    val comment: String = ""
)