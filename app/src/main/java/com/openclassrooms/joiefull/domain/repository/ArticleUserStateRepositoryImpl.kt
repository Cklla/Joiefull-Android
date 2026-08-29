package com.openclassrooms.joiefull.domain.repository

import com.openclassrooms.joiefull.domain.model.ArticleUserState
import com.openclassrooms.joiefull.domain.repository.ArticleUserStateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleUserStateRepositoryImpl @Inject constructor() : ArticleUserStateRepository {

    private val _userStates = MutableStateFlow<Map<Int, ArticleUserState>>(emptyMap())

    override fun observeUserStates(): Flow<Map<Int, ArticleUserState>> = _userStates.asStateFlow()

    override fun toggleFavorite(articleId: Int) {
        updateState(articleId) { it.copy(isFavorite = !it.isFavorite) }
    }

    override fun setRating(articleId: Int, rating: Int) {
        updateState(articleId) { it.copy(rating = rating) }
    }

    override fun setComment(articleId: Int, comment: String) {
        updateState(articleId) { it.copy(comment = comment) }
    }

    private fun updateState(articleId: Int, transform: (ArticleUserState) -> ArticleUserState) {
        _userStates.update { current ->
            val existing = current[articleId] ?: ArticleUserState()
            current + (articleId to transform(existing))
        }
    }
}