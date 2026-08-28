package com.openclassrooms.joiefull.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.Category

@Composable
fun HomeContent(
    articlesByCategory: Map<Category, List<Article>>,
    onArticleClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        articlesByCategory.forEach { (category, articles) ->
            item(key = category.name) {
                CategorySection(
                    category = category,
                    articles = articles,
                    onArticleClick = onArticleClick
                )
            }
        }
    }
}

@Composable
private fun CategorySection(
    category: Category,
    articles: List<Article>,
    onArticleClick: (Int) -> Unit
) {
    Column {
        Text(
            text = category.displayName(),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .semantics { heading() }
        )
        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(articles, key = { it.id }) { article ->
                ArticleCard(
                    article = article,
                    modifier = Modifier
                        .width(170.dp)
                        .clickable(
                            onClickLabel = stringResource(R.string.article_click_label),
                        ) { onArticleClick(article.id) }
                        .semantics(mergeDescendants = true) {}
                )
            }
        }
    }
}

@Composable
private fun Category.displayName(): String = when (this) {
    Category.TOPS -> stringResource(R.string.category_tops)
    Category.BOTTOMS -> stringResource(R.string.category_bottoms)
    Category.SHOES -> stringResource(R.string.category_shoes)
    Category.ACCESSORIES -> stringResource(R.string.category_accessories)
    Category.UNKNOWN -> stringResource(R.string.category_unknown)
}
