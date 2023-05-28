package org.android.go.sopt.presentation.home

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityHomeBinding
import org.android.go.sopt.util.BindingActivity

class HomeActivity : BindingActivity<ActivityHomeBinding>(R.layout.activity_home) {
    private val homeVm by viewModels<HomeViewModel>()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) {
            //binding.ivProfile.load(it[0])
            //binding.ivProfile2.load(it[1])
            //binding.ivProfile3.load(it[2])
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeVm.listUsers()

        homeVm.listUsersResult.observe(this) {
            binding.rv.adapter = HomeAdapter(homeVm.listUsersResult.value!!)
        }

        binding.ivProfile.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

    }
}