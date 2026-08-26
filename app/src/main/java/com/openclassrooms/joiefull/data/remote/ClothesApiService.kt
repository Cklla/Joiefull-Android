package com.openclassrooms.joiefull.data.remote

import com.openclassrooms.joiefull.data.remote.dto.ArticleDto
import retrofit2.http.GET

interface ClothesApiService {

    @GET("OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main /api/clothes.json")
    suspend fun getArticles(): List<ArticleDto>
}