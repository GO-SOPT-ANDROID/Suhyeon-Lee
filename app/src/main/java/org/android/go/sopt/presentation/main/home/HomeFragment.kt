package org.android.go.sopt.presentation.main.home

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.showLoadingDialog

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val homeVm: HomeViewModel by viewModels()
    private lateinit var dialog: Dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        displayScreen()
    }

    private fun registerObserver() {
        homeVm.listUsersResult.observe(viewLifecycleOwner) {
            binding.rv.adapter = HomeAdapter(homeVm.listUsersResult.value!!)
        }

        homeVm.dialogFlag.observe(viewLifecycleOwner) {
            Log.d("ABC", "dialog became $it")
            if (it) requireActivity().showLoadingDialog(dialog, "Loading data...")
            else dialog.cancel()
        }
    }

    private fun displayScreen() {
        dialog = Dialog(requireContext())
        homeVm.setDialogFlag(true)
        homeVm.listUsers()
    }
}