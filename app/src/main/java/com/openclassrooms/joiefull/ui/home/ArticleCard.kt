package com.openclassrooms.joiefull.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.Category
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import com.openclassrooms.joiefull.domain.model.ArticleUiModel
import com.openclassrooms.joiefull.domain.model.ArticleUserState

@Composable
fun ArticleCard(
    articleUiModel: ArticleUiModel,
    modifier: Modifier = Modifier,
) {
    val article = articleUiModel.article
    val likesContentDescription = stringResource(R.string.article_likes_count, article.likes)

    Column(modifier = modifier.fillMaxWidth()) {
        Box {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.imageDescription,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .semantics(mergeDescendants = true) {
                        contentDescription = likesContentDescription
                    }
            ) {
                Icon(
                    imageVector = Icons.Filled.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.article_likes_count_visible, article.likes),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .clearAndSetSemantics {}
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text(
                text = article.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f)
            )
            PriceLabel(price = article.price, originalPrice = article.originalPrice)
        }
        if (articleUiModel.userState.rating > 0) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.article_rating, articleUiModel.userState.rating),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun PriceLabel(price: Double, originalPrice: Double) {
    Column(horizontalAlignment = Alignment.End) {
        Text(
            text = stringResource(R.string.article_price, price),
            style = MaterialTheme.typography.titleMedium,
        )
        if (originalPrice != price) {
            Text(
                text = stringResource(R.string.article_price, originalPrice),
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.LineThrough
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 170)
@Composable
private fun ArticleCardPreview() {
    JoiefullTheme {
        ArticleCard(
            articleUiModel = ArticleUiModel(
                article = Article(
                    id = 1,
                    imageUrl = "https://raw.githubusercontent.com/.../image.jpg",
                    imageDescription = "T-shirt blanc à rayures",
                    name = "T-shirt rayé",
                    category = Category.TOPS,
                    likes = 3,
                    price = 19.99,
                    originalPrice = 24.99,
                ),
                userState = ArticleUserState(isFavorite = true, rating = 4),
            ),
        )
    }
}