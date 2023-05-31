package org.android.go.sopt.presentation.main.home

import android.view.MotionEvent
import androidx.annotation.Nullable
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView

/*
 * ItemDetailsLookUp<T> : 사용자의 선택과 관련된 item에 대한 정보를 제공한다
 * MotionEvent를 기반으로, 선택된 내용을 ViewHolder에 매핑한다
*/
class ItemDetailsLookUp(val rv: RecyclerView): ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = rv.findChildViewUnder(e.x, e.y)
        if (view != null) {
            val viewHolder = rv.getChildViewHolder(view) as HomeAdapter.HomeViewHolder
            return viewHolder.getItemDetails()
        }
        return null
    }

}