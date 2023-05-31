package org.android.go.sopt.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.databinding.ItemHomeRvBinding

class HomeAdapter
    : ListAdapter<ResUsersDto.Data, RecyclerView.ViewHolder>(HomeDiffCallback) {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            ItemHomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curItem = currentList[position]
        with((holder as HomeViewHolder).binding) {
            ivProfile.load(curItem.avatar)
            tvName.text = "${curItem.lastName} ${curItem.firstName}"
            tvEmail.text = curItem.email
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class HomeViewHolder(val binding: ItemHomeRvBinding) :
        RecyclerView.ViewHolder(binding.root)
}

object HomeDiffCallback : DiffUtil.ItemCallback<ResUsersDto.Data>() {
    override fun areItemsTheSame(oldItem: ResUsersDto.Data, newItem: ResUsersDto.Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResUsersDto.Data, newItem: ResUsersDto.Data): Boolean {
        return oldItem == newItem
    }

}