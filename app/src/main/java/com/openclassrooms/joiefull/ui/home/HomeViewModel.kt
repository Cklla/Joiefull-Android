package com.openclassrooms.joiefull.ui.home

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
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val articleUserStateRepository: ArticleUserStateRepository
) : ViewModel() {

    private val articlesResult = flow { emit(articlesRepository.getArticles()) }

    val uiState: StateFlow<HomeUiState> = combine(
        articlesResult,
        articleUserStateRepository.observeUserStates(),
    ) { result, userStates ->
        result.fold(
            onSuccess = { articles ->
                val uiModels = articles.map { article ->
                    ArticleUiModel(
                        article = article,
                        userState = userStates[article.id] ?: ArticleUserState(),
                    )
                }
                HomeUiState.Success(uiModels.groupBy { it.article.category })
            },
            onFailure = { throwable ->
                HomeUiState.Error(throwable.message ?: "Erreur inconnue")
            }
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = HomeUiState.Loading,
    )
}