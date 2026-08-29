package com.openclassrooms.joiefull.domain.model

data class ArticleUiModel(
    val article: Article,
    val userState: ArticleUserState,
) {
    val displayedLikes: Int
        get() = article.likes + if (userState.isFavorite) 1 else 0
}
