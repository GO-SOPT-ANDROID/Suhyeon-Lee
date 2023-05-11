package org.android.go.sopt.RecyclerView

import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.RecyclerView.Data.DataModel
import org.android.go.sopt.databinding.ItemRvProfileBinding

class ViewHolderProfile(private val binding: ItemRvProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DataModel) {
        val viewObj = item.viewObject as ViewObject.Profile
        binding.tvTitle.text = viewObj.title
        binding.tvContent.text = viewObj.content
    }
}