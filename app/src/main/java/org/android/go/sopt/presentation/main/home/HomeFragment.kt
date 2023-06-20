package org.android.go.sopt.presentation.main.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import org.android.go.sopt.R
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.databinding.FragmentHomeBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val mainVm: MainViewModel by activityViewModels()
    private val homeVm: HomeViewModel by viewModels()

    private lateinit var rv: RecyclerView
    private lateinit var homeAdapter: HomeAdapter
    private lateinit var fragSelTracker: SelectionTracker<Long>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()
        registerClickEvents()
        displayScreen()
    }

    private fun registerObserver() {
        homeVm.listUsersResult.observe(viewLifecycleOwner) {
            homeAdapter.submitList(it.toMutableList())
        }
        binding.vm = homeVm
        homeVm.btnDelete.observe(viewLifecycleOwner) {
            Log.d("ABC", "[observer] btnDelete is $it")
            if (it) { // delete 가능하게 활성화돼야
                homeAdapter.setSelBoolean(true)
            }
            else { // 'edit'
                homeAdapter.setSelBoolean(false)
            }
        }
    }

    private fun registerClickEvents() {
        binding.btnDelete.setOnClickListener {
            if (homeVm.btnDelete.value == true) { // true -> false 바뀌기 직전
                homeAdapter.removeItem(fragSelTracker.selection)
            }
            homeVm.clickBtnDelete() // inverter
        }
    }

    private fun displayScreen() {
        mainVm.setDialogFlag(true)
        homeVm.listUsers(mainVm)
        initRecyclerView()
    }

    private fun initRecyclerView() {
        rv = binding.rv
        homeAdapter = HomeAdapter()
        rv.adapter = homeAdapter
        addTrackObserver()
    }

    private fun addTrackObserver() {
        fragSelTracker = setSelectionTracker("selTracker", rv)
        fragSelTracker.addObserver((object: SelectionTracker.SelectionObserver<Long>() {
            override fun onSelectionRefresh() {
                super.onSelectionRefresh()
                val items = fragSelTracker.selection.size()
                if (items == 0) binding.btnDelete.isEnabled = false
                else binding.btnDelete.isEnabled = items >= 1
            }
        }))
        homeAdapter.setSelTracker(fragSelTracker)
    }
}