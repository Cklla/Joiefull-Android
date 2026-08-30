package com.openclassrooms.joiefull.ui.detail

import com.openclassrooms.joiefull.domain.model.ArticleUiModel

sealed interface DetailUiState {
    data object Loading: DetailUiState
    data class Success(val articleUiModel: ArticleUiModel) : DetailUiState
    data class Error(val message: String) : DetailUiState
}