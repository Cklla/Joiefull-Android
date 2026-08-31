package com.openclassrooms.joiefull.ui.detail

import org.junit.Assert.assertEquals
import org.junit.Test

class ShareTestBuildTest {

    @Test
    fun `buildArticleShareText includes name, price and link`() {
        val shareText = buildArticleShareText(
            articleName = "Pull torsadé",
            formattedPrice = "69,99 €",
            comment = "",
            articleId = 1,
        )

        assertEquals(
            "Pull torsadé - 69,99 €\n\njoiefull://article/1",
            shareText
        )
    }

    @Test
    fun `buildArticleShareText includes comment when present`() {
        val shareText = buildArticleShareText(
            articleName = "Pull torsadé",
            formattedPrice = "69,99 €",
            comment = "Super doux !",
            articleId = 1,
        )

        assertEquals(
            "Pull torsadé - 69,99 €\n\nMon avis : Super doux !\n\njoiefull://article/1",
            shareText
        )
    }

    @Test
    fun `buildArticleShareText ignores blank comment`() {
        val shareText = buildArticleShareText(
            articleName = "Pull torsadé",
            formattedPrice = "69,99 €",
            comment = "   ",
            articleId = 1,
        )

        assertEquals(
            "Pull torsadé - 69,99 €\n\njoiefull://article/1",
            shareText
        )
    }
}