package com.openclassrooms.joiefull.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfoV2
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.openclassrooms.joiefull.ui.detail.DetailEmptyState
import com.openclassrooms.joiefull.ui.detail.DetailScreen
import com.openclassrooms.joiefull.ui.home.HomeScreen

@Composable
fun JoiefullApp(modifier: Modifier = Modifier) {
    var selectedArticleId by rememberSaveable { mutableStateOf<Int?>(null) }
    val articleId = selectedArticleId
    val windowSizeClass = currentWindowAdaptiveInfoV2().windowSizeClass

    if (isMasterDetailLayout(windowSizeClass)) {
        Row(modifier = modifier.fillMaxSize()) {
            HomeScreen(
                onArticleClick = { id -> selectedArticleId = id },
                modifier = Modifier.weight(3f)
            )
            Box(modifier = Modifier.weight(2f)) {
                if (articleId != null) {
                    DetailScreen(
                        articleId = articleId,
                        onBackClick = { selectedArticleId = null },
                        showBackButton = false,
                    )
                } else {
                    DetailEmptyState()
                }
            }
        }
    } else if (articleId == null) {
        HomeScreen(
            onArticleClick = { id -> selectedArticleId = id },
            modifier = modifier
        )
    } else {
        BackHandler {
            selectedArticleId = null
        }
        DetailScreen(
            articleId = articleId,
            onBackClick = { selectedArticleId = null },
            modifier = modifier
        )
    }
}