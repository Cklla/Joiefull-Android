package com.openclassrooms.joiefull.ui.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.openclassrooms.joiefull.ui.detail.DetailScreen
import com.openclassrooms.joiefull.ui.home.HomeScreen

@Composable
fun JoiefullApp(modifier: Modifier = Modifier) {
    var selectedArticledId by rememberSaveable { mutableStateOf<Int?>(null) }
    val articleId = selectedArticledId

    if(articleId == null) {
        HomeScreen(
            onArticleClick = { id -> selectedArticledId = id },
            modifier = modifier
        )
    } else {
        BackHandler {
            selectedArticledId = null
        }
        DetailScreen(
            articleId = articleId,
            onBackClick = { selectedArticledId = null },
            modifier = modifier
        )
    }
}