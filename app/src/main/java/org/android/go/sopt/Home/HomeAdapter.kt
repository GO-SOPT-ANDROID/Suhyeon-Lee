package org.android.go.sopt.Home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.android.go.sopt.Data.Model.ResUsersDto
import org.android.go.sopt.databinding.ItemRvHomeBinding

class HomeAdapter(private val userList: List<ResUsersDto.Data>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HomeViewHolder(
            ItemRvHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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

    class HomeViewHolder(val binding: ItemRvHomeBinding) : RecyclerView.ViewHolder(binding.root)
}