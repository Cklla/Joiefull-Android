package com.openclassrooms.joiefull.ui.detail

fun buildArticleShareText(
    articleName: String,
    formattedPrice: String,
    comment: String,
    articleId: Int
) : String = buildString {
    append(articleName)
    append(" - ")
    append(formattedPrice)
    appendLine()
    appendLine()
    if (comment.isNotBlank()) {
        append("Mon avis : ")
        appendLine(comment)
        appendLine()
    }
    append("joiefull://article/$articleId")
}