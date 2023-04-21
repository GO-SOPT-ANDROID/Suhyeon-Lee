package org.android.go.sopt.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.RecyclerView.Data.DataModel
import org.android.go.sopt.RecyclerView.Data.ViewType
import org.android.go.sopt.databinding.ItemRvHeaderBinding
import org.android.go.sopt.databinding.ItemRvProfileBinding

class MultiViewAdapter(private val list: ArrayList<DataModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ViewType.HEADER.ordinal -> ViewHolderHeader(
                ItemRvHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            ViewType.PROFILE.ordinal -> ViewHolderProfile(
                ItemRvProfileBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
            else -> throw java.lang.IllegalArgumentException("invalid item type")
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        return when (list[position].viewType) {
            ViewType.HEADER.ordinal -> (holder as ViewHolderHeader).bind(list[position])
            ViewType.PROFILE.ordinal -> (holder as ViewHolderProfile).bind(list[position])
            else -> throw java.lang.IllegalArgumentException("invalid item type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].viewType
    }
}