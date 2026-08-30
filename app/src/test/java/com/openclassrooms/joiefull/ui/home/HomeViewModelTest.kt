package com.openclassrooms.joiefull.ui.home

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
class HomeViewModelTest {

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private val topArticle = Article(
        id = 1, imageUrl = "url1", imageDescription = "desc1",
        name = "Top", category = Category.TOPS, likes = 5, price = 10.0, originalPrice = 10.0,
    )
    private val bottomArticle = Article(
        id = 2, imageUrl = "url2", imageDescription = "desc2",
        name = "Pantalon", category = Category.BOTTOMS, likes = 3, price = 20.0, originalPrice = 25.0,
    )

    private fun createViewModel(
        articles: List<Article> = emptyList(),
        error: Throwable? = null,
        userStateRepository: ArticleUserStateRepositoryImpl = ArticleUserStateRepositoryImpl(),
    ) = HomeViewModel(
        articlesRepository = FakeArticlesRepository(articles = articles, error = error),
        articleUserStateRepository = userStateRepository,
    )

    @Test
    fun `uiState is Loading before articles are fetched`() {
        val viewModel = createViewModel(articles = listOf(topArticle))

        assertEquals(HomeUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState is Success with articles grouped by category`() {
        val viewModel = createViewModel(articles = listOf(topArticle, bottomArticle))

        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is HomeUiState.Success)
        assertEquals(
            mapOf(
                Category.TOPS to listOf(ArticleUiModel(topArticle, ArticleUserState())),
                Category.BOTTOMS to listOf(ArticleUiModel(bottomArticle, ArticleUserState())),
            ),
            (state as HomeUiState.Success).articlesByCategory,
        )
    }

    @Test
    fun `uiState is Error when repository fails`() {
        val viewModel = createViewModel(error = IOException("network error"))

        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is HomeUiState.Error)
    }

    @Test
    fun `uiState reflects favorite toggled after articles are loaded`() {
        val userStateRepository = ArticleUserStateRepositoryImpl()
        val viewModel = createViewModel(
            articles = listOf(topArticle),
            userStateRepository = userStateRepository,
        )
        testDispatcher.scheduler.advanceUntilIdle()

        userStateRepository.toggleFavorite(topArticle.id)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value as HomeUiState.Success
        val uiModel = state.articlesByCategory.getValue(Category.TOPS).single()
        assertTrue(uiModel.userState.isFavorite)
        assertEquals(topArticle.likes + 1, uiModel.displayedLikes)
    }
}