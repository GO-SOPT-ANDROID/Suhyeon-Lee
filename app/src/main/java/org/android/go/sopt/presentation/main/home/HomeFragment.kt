package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val mainVm: MainViewModel by activityViewModels()
    private val homeVm: HomeViewModel by viewModels()

    private val homeAdapter = HomeAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        displayScreen()
    }

    private fun registerObserver() {
        homeVm.listUsersResult.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it)
        }
    }

    private fun displayScreen() {
        mainVm.setDialogFlag(true)
        homeVm.listUsers(mainVm)
        binding.rv.adapter = homeAdapter
    }
}