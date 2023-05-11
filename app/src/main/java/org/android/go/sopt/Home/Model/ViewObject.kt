package org.android.go.sopt.Home

sealed class ViewObject {
    data class Header(
        val str: String
    ) : ViewObject()

    data class Profile(
        val title: String,
        val content: String
    ) : ViewObject()
}