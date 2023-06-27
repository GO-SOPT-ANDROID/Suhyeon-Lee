package org.android.go.sopt.presentation.main.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.model.ResGetMusicListDto
import org.android.go.sopt.databinding.ItemMusicRvBinding

class MusicAdapter
    : ListAdapter<ResGetMusicListDto.Data.Music, RecyclerView.ViewHolder>(MusicDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return MusicViewHolder(
            ItemMusicRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curItem = currentList[position]
        with((holder as MusicViewHolder).binding) {
            ivAlbum.load(curItem.url)
            tvTitle.text = curItem.title
            tvSinger.text = curItem.singer
        }
    }

    class MusicViewHolder(val binding: ItemMusicRvBinding): RecyclerView.ViewHolder(binding.root)

}

object MusicDiffCallback : DiffUtil.ItemCallback<ResGetMusicListDto.Data.Music>() {
    override fun areItemsTheSame(
        oldItem: ResGetMusicListDto.Data.Music,
        newItem: ResGetMusicListDto.Data.Music
    ): Boolean {
        return oldItem.url == newItem.url
    }

    override fun areContentsTheSame(
        oldItem: ResGetMusicListDto.Data.Music,
        newItem: ResGetMusicListDto.Data.Music
    ): Boolean {
        return oldItem == newItem
    }
}