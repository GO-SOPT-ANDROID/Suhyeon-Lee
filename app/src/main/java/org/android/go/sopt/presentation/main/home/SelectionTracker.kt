package org.android.go.sopt.presentation.main.home

import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StableIdKeyProvider
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.RecyclerView

fun setSelectionTracker(id: String, rv: RecyclerView): SelectionTracker<Long> {
    val tracker = SelectionTracker.Builder(
        id,
        rv,
        StableIdKeyProvider(rv),
        ItemDetailsLookUp(rv),
        StorageStrategy.createLongStorage()
    ).withSelectionPredicate(object: SelectionTracker.SelectionPredicate<Long>() {
        override fun canSetStateForKey(key: Long, nextState: Boolean): Boolean {
            return true
        }

        override fun canSetStateAtPosition(position: Int, nextState: Boolean): Boolean {
            return true
        }

        override fun canSelectMultiple(): Boolean {
            return true
        }
    }).build()
    return tracker
}