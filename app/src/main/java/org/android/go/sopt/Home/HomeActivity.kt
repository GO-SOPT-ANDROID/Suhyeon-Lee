package org.android.go.sopt.Home

import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.android.go.sopt.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val homeVm by viewModels<HomeViewModel>()

    private val launcher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) {
        //binding.ivProfile.load(it[0])
        //binding.ivProfile2.load(it[1])
        //binding.ivProfile3.load(it[2])
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        homeVm.listUsers()

        homeVm.listUsersResult.observe(this) {
            binding.rv.adapter = HomeAdapter(homeVm.listUsersResult.value!!)
        }

        binding.ivProfile.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

    }
}