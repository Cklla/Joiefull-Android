package com.openclassrooms.joiefull.ui.home

import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.ArticleUiModel
import com.openclassrooms.joiefull.domain.model.Category

sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class Success(val articlesByCategory: Map<Category, List<ArticleUiModel>>) : HomeUiState
    data class Error(val message: String) : HomeUiState
}