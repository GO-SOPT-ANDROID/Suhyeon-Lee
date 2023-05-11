package org.android.go.sopt.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.RecyclerView.Data.DataModel
import org.android.go.sopt.databinding.ItemRvHeaderBinding

class ViewHolderHeader(private val binding: ItemRvHeaderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DataModel) {
        val viewObj = item.viewObject as ViewObject.Header
        binding.tvGibberish.text = viewObj.str
    }
}