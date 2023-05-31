package org.android.go.sopt.presentation.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.selection.ItemDetailsLookup.ItemDetails
import androidx.recyclerview.selection.Selection
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.databinding.ItemHomeRvBinding


class HomeAdapter
    : ListAdapter<ResUsersDto.Data, RecyclerView.ViewHolder>(HomeDiffCallback) {

    private lateinit var adSelTracker: SelectionTracker<Long>
    private var activateSel = false

    init {
        setHasStableIds(true) // 'id를 이용해 item을 식별하겠다' (selection library: key = Long type)
    }

    // Return the stable ID for the item at position
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    class HomeViewHolder(val binding: ItemHomeRvBinding) : RecyclerView.ViewHolder(binding.root) {
        // ItemDetails<T> : 추상클래스 - getPosition(), getSelectionKey() 구현하게끔
        fun getItemDetails(): ItemDetails<Long> =
            object : ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long = itemId
            }
    }

    fun setSelTracker(tracker: SelectionTracker<Long>) {
        this.adSelTracker = tracker
    }

    fun setSelBoolean(b: Boolean) {
        this.activateSel = b
    }

    fun removeItem(sel: Selection<Long>) {
        val curList = currentList.toMutableList()
        val eraseList = mutableListOf<ResUsersDto.Data>()
        sel.forEach {
            Log.e("ABC", "[sel.forEach] $it")
            eraseList.add(curList[it.toInt()])
        }
        curList.removeAll(eraseList)
        //submitList(null)
        submitList(curList)
        adSelTracker.clearSelection()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            ItemHomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curItem = currentList[position]
        with((holder as HomeViewHolder).binding) {
            if (!activateSel) {
                root.isClickable = false
                selected = false
            }
            else {
                root.isClickable = true
                root.setOnClickListener {
                    Log.d("ABC", "selected ${adSelTracker.selection}") //TODO: 이거 왜 안 찍힘?
                    adSelTracker.select(position.toLong())
                }
                selected = adSelTracker.isSelected(position.toLong())
            }

            ivProfile.load(curItem.avatar)
            tvName.text = "${curItem.lastName} ${curItem.firstName}"
            tvEmail.text = curItem.email
        }
    }
}

object HomeDiffCallback : DiffUtil.ItemCallback<ResUsersDto.Data>() {
    override fun areItemsTheSame(oldItem: ResUsersDto.Data, newItem: ResUsersDto.Data): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ResUsersDto.Data, newItem: ResUsersDto.Data): Boolean {
        return oldItem == newItem
    }
}