package org.android.go.sopt.util

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

fun RecyclerView.setDivider(@DrawableRes drawableRes: Int) {
    val divider = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
    val dividerDrawableRes = ContextCompat.getDrawable(context, drawableRes)
    if (dividerDrawableRes != null) {
        divider.setDrawable(dividerDrawableRes)
    }
    addItemDecoration(divider)
}

inline fun View.onClick(crossinline onClick: () -> Unit) {
    setOnClickListener { onClick() }
}