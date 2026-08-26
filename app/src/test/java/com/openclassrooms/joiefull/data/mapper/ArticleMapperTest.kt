package com.openclassrooms.joiefull.data.mapper

import com.openclassrooms.joiefull.data.remote.dto.ArticleDto
import com.openclassrooms.joiefull.data.remote.dto.PictureDto
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.Category
import org.junit.Assert.assertEquals
import org.junit.Test

class ArticleMapperTest {

    @Test
    fun `toDomain maps all fields correctly`() {
        val dto = ArticleDto(
            id = 1,
            picture = PictureDto(url = "https://example.com/image.png", description = "Une robe rouge"),
            name = "Robe Rouge",
            category = "TOPS",
            likes = 12,
            price = 39.99,
            originalPrice = 49.99
        )

        val article = dto.toDomain()

        assertEquals(1, article.id)
        assertEquals("https://example.com/image.png", article.imageUrl)
        assertEquals("Une robe rouge", article.imageDescription)
        assertEquals("Robe Rouge", article.name)
        assertEquals(Category.TOPS, article.category)
        assertEquals(12, article.likes)
        assertEquals(39.99, article.price, 0.0)
        assertEquals(49.99, article.originalPrice, 0.0)
    }

    @Test
    fun `toDomain maps unknown category to UNKNOWN`() {
        val dto = ArticleDto(
            id = 2,
            picture = PictureDto(url = "https://example.com/image2.png", description = "..."),
            name = "Article mystère",
            category = "GLOVES",
            likes = 0,
            price = 10.0,
            originalPrice = 10.0
        )

        val article = dto.toDomain()

        assertEquals(Category.UNKNOWN, article.category)
    }
}