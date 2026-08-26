package com.openclassrooms.joiefull.data.repository

import com.openclassrooms.joiefull.data.mapper.toDomain
import com.openclassrooms.joiefull.data.remote.ClothesApiService
import com.openclassrooms.joiefull.data.remote.dto.ArticleDto
import com.openclassrooms.joiefull.data.remote.dto.PictureDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.IOException


private class FakeClothesApiService(
    private val articles: List<ArticleDto> = emptyList(),
    private val error: Throwable? = null,
) : ClothesApiService {
    override suspend fun getArticles() : List<ArticleDto> {
        error?.let { throw it }
        return articles
    }
}

class ArticlesRepositoryImplTest {

    private val dto = ArticleDto(
        id = 1,
        picture = PictureDto(url = "https://example.com/image.png", description = "Une robe rouge"),
        name = "Robe rouge",
        category = "TOPS",
        likes = 12,
        price = 39.99,
        originalPrice = 49.99
    )

    @Test
    fun `getArticles returns mapped articles on success`() = runTest {
        val repository = ArticlesRepositoryImpl(FakeClothesApiService(articles = listOf(dto)))

        val result = repository.getArticles()

        assertTrue(result.isSuccess)
        assertEquals(listOf(dto.toDomain()), result.getOrNull())
    }

    @Test
    fun `getArticles returns failure when api throws`() = runTest {
        val repository = ArticlesRepositoryImpl(FakeClothesApiService(error = IOException("network error")))

        val result = repository.getArticles()

        assertTrue(result.isFailure)
    }
}