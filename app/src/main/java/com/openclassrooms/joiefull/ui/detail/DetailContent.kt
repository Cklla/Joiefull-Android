package com.openclassrooms.joiefull.ui.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.openclassrooms.joiefull.R
import com.openclassrooms.joiefull.domain.model.Article
import com.openclassrooms.joiefull.domain.model.ArticleUiModel
import com.openclassrooms.joiefull.domain.model.ArticleUserState
import com.openclassrooms.joiefull.domain.model.Category
import com.openclassrooms.joiefull.ui.theme.JoiefullTheme

@Composable
fun DetailContent(
    articleUiModel: ArticleUiModel,
    onFavoriteClick: () -> Unit,
    onRatingChange: (Int) -> Unit,
    onCommentChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val article = articleUiModel.article
    val userState = articleUiModel.userState

    Column(modifier = modifier.verticalScroll(rememberScrollState())) {
        Box {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
                    .minimumInteractiveComponentSize()
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.surface)
                    .clickable(
                        onClickLabel = stringResource(
                            if (userState.isFavorite) R.string.favorite_remove_content_description
                            else R.string.favorite_add_content_description
                        ),
                        onClick = onFavoriteClick,
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
                    .semantics(mergeDescendants = true) {}
            ) {
                Icon(
                    imageVector = if (userState.isFavorite) Icons.Filled.Favorite else
                        Icons.Filled.FavoriteBorder,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = stringResource(R.string.article_likes_count, articleUiModel.displayedLikes),
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = article.name,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f)
                )
                if (userState.rating > 0) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = stringResource(R.string.article_rating, userState.rating),
                            style = MaterialTheme.typography.labelMedium,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(R.string.article_price, article.price),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.weight(1f)
                )
                if (article.originalPrice != article.price) {
                    Text(
                        text = stringResource(R.string.article_price, article.originalPrice),
                        style = MaterialTheme.typography.bodySmall,
                        textDecoration = TextDecoration.LineThrough
                    )
                }
            }
            Text(
                text = article.imageDescription,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 12.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                RatingStars(
                    rating = userState.rating,
                    onRatingChange = onRatingChange,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
            OutlinedTextField(
                value = userState.comment,
                onValueChange = onCommentChange,
                placeholder = { Text(stringResource(R.string.comment_placeholder)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun RatingStars(
    rating: Int,
    onRatingChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        for (starIndex in 1..5) {
            Icon(
                imageVector = if (starIndex <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = stringResource(R.string.rating_star_content_description, starIndex),
                tint = if (starIndex <= rating) MaterialTheme.colorScheme.primary else
                    MaterialTheme.colorScheme.outline,
                modifier = Modifier
                    .minimumInteractiveComponentSize()
                    .clickable(onClick = { onRatingChange(starIndex) })
                    .padding(4.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DetailContentPreview() {
    JoiefullTheme {
        DetailContent(
            articleUiModel = ArticleUiModel(
                article = Article(
                    id = 1,
                    imageUrl = "https://raw.githubusercontent.com/OpenClassrooms-Student-Center/D-velopper-une-interface-accessible-en-Jetpack-Compose/main/img/tops/1.jpg",
                    imageDescription = "Pull vert forêt à motif torsadé, manches bouffantes et col montant",
                    name = "Pull torsadé",
                    category = Category.TOPS,
                    likes = 55,
                    price = 69.99,
                    originalPrice = 95.0,
                ),
                userState = ArticleUserState(isFavorite = true, rating = 4, comment = ""),
            ),
            onFavoriteClick = {},
            onRatingChange = {},
            onCommentChange = {},
        )
    }
}