package com.openclassrooms.joiefull.data.repository

import com.openclassrooms.joiefull.data.mapper.toDomain
import com.openclassrooms.joiefull.data.remote.ClothesApiService
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.repository.ArticlesRepository
import javax.inject.Inject

class ArticlesRepositoryImpl @Inject constructor(
    private val api: ClothesApiService,
) : ArticlesRepository {

    override suspend fun getArticles(): Result<List<Article>> = runCatching {
        api.getArticles().map { it.toDomain() }
    }
}