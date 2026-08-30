package com.openclassrooms.joiefull.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.joiefull.domain.model.ArticleUiModel
import com.openclassrooms.joiefull.domain.model.ArticleUserState
import com.openclassrooms.joiefull.domain.repository.ArticleUserStateRepository
import com.openclassrooms.joiefull.domain.repository.ArticlesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val articleUserStateRepository: ArticleUserStateRepository
) : ViewModel() {

    private val articlesResult = flow { emit(articlesRepository.getArticles()) }
    private val requestedArticleId = MutableStateFlow<Int?>(null)

    val uiState: StateFlow<DetailUiState> = combine(
        articlesResult,
        articleUserStateRepository.observeUserStates(),
        requestedArticleId,
    ) { result, userStates, articleId ->
        if (articleId == null) {
            return@combine DetailUiState.Loading
        }
        result.fold(
            onSuccess = { articles ->
                val article = articles.find { it.id == articleId }
                if (article != null) {
                    DetailUiState.Success(
                        ArticleUiModel(
                            article = article,
                            userState = userStates[articleId] ?: ArticleUserState(),
                        )
                    )
                } else {
                    DetailUiState.Error("Article introuvable")
                }
            },
            onFailure = { throwable ->
                DetailUiState.Error(throwable.message ?: "Erreur inconnue")
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = DetailUiState.Loading
    )

    fun loadArticle(articleId: Int) {
        requestedArticleId.value = articleId
    }

    fun toggleFavorite(articleId: Int) {
        articleUserStateRepository.toggleFavorite(articleId)
    }

    fun setRating(articleId: Int, rating: Int) {
        articleUserStateRepository.setRating(articleId, rating)
    }

    fun setComment(articleId: Int, comment: String) {
        articleUserStateRepository.setComment(articleId, comment)
    }
}