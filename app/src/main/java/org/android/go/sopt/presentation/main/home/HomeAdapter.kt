package org.android.go.sopt.presentation.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.databinding.ItemHomeRvBinding

class HomeAdapter(private val userList: List<ResUsersDto.Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            ItemHomeRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val curItem = userList[position]
        with((holder as HomeViewHolder).binding) {
            tvName.text = curItem.firstName + " " + curItem.lastName
            tvEmail.text = curItem.email
            ivProfile.load(curItem.avatar)
        }
    }

    class HomeViewHolder(val binding: ItemHomeRvBinding) : RecyclerView.ViewHolder(binding.root)
}