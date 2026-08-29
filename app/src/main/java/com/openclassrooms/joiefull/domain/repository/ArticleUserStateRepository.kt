package com.openclassrooms.joiefull.domain.repository

import com.openclassrooms.joiefull.domain.model.ArticleUserState
import kotlinx.coroutines.flow.Flow

interface ArticleUserStateRepository {
    fun observeUserStates(): Flow<Map<Int, ArticleUserState>>
    fun toggleFavorite(articleId: Int)
    fun setRating(articleId: Int, rating: Int)
    fun setComment(articleId: Int, comment: String)
}