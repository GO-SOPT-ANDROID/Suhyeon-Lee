package org.android.go.sopt.RecyclerView

sealed class ViewObject {
    data class Header(
        val str: String
    ) : ViewObject()

    data class Profile(
        val title: String,
        val content: String
    ) : ViewObject()
}