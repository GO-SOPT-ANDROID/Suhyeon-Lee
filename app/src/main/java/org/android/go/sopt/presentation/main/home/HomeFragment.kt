package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.util.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeVm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        homeVm.listUsers()
    }

    private fun registerObserver() {
        homeVm.listUsersResult.observe(viewLifecycleOwner) {
            binding.rv.adapter = HomeAdapter(homeVm.listUsersResult.value!!)
        }
    }
}