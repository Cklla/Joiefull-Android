package com.openclassrooms.joiefull.domain.repository

import com.openclassrooms.joiefull.domain.model.Article

interface ArticlesRepository {
    suspend fun getArticles(): Result<List<Article>>
}