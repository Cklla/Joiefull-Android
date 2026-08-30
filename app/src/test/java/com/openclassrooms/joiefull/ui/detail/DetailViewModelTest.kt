package com.openclassrooms.joiefull.ui.detail

import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.ArticleUiModel
import com.openclassrooms.joiefull.domain.model.ArticleUserState
import com.openclassrooms.joiefull.domain.model.Category
import com.openclassrooms.joiefull.data.repository.ArticleUserStateRepositoryImpl
import com.openclassrooms.joiefull.domain.repository.ArticlesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

private class FakeArticlesRepository(
    private val articles: List<Article> = emptyList(),
    private val error: Throwable? = null,
) : ArticlesRepository {
    override suspend fun getArticles(): Result<List<Article>> {
        return error?.let { Result.failure(it) } ?: Result.success(articles)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val article = Article(
        id = 1, imageUrl = "url1", imageDescription = "desc1",
        name = "Pull torsadé", category = Category.TOPS, likes = 5, price = 69.99, originalPrice = 95.0,
    )

    private fun createViewModel(
        articles: List<Article> = emptyList(),
        error: Throwable? = null,
        userStateRepository: ArticleUserStateRepositoryImpl = ArticleUserStateRepositoryImpl(),
    ) = DetailViewModel(
        articlesRepository = FakeArticlesRepository(articles = articles, error = error),
        articleUserStateRepository = userStateRepository,
    )

    @Test
    fun `uiState is Loading before loadArticle is called`() {
        val viewModel = createViewModel(articles = listOf(article))

        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(DetailUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState is Success with matching article after loadArticle`() {
        val viewModel = createViewModel(articles = listOf(article))

        viewModel.loadArticle(article.id)
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(
            DetailUiState.Success(ArticleUiModel(article, ArticleUserState())),
            viewModel.uiState.value,
        )
    }

    @Test
    fun `uiState is Error when repository fails`() {
        val viewModel = createViewModel(error = IOException("network error"))

        viewModel.loadArticle(article.id)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is DetailUiState.Error)
    }

    @Test
    fun `uiState is Error when no article matches the requested id`() {
        val viewModel = createViewModel(articles = listOf(article))

        viewModel.loadArticle(999)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is DetailUiState.Error)
    }

    @Test
    fun `uiState reflects rating and comment updates after article is loaded`() {
        val userStateRepository = ArticleUserStateRepositoryImpl()
        val viewModel = createViewModel(
            articles = listOf(article),
            userStateRepository = userStateRepository,
        )
        viewModel.loadArticle(article.id)
        testDispatcher.scheduler.advanceUntilIdle()

        viewModel.toggleFavorite(article.id)
        viewModel.setRating(article.id, 4)
        viewModel.setComment(article.id, "Très confortable")
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value as DetailUiState.Success
        assertEquals(true, state.articleUiModel.userState.isFavorite)
        assertEquals(4, state.articleUiModel.userState.rating)
        assertEquals("Très confortable", state.articleUiModel.userState.comment)
    }
}