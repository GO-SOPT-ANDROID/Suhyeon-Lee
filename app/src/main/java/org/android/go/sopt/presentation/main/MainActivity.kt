package org.android.go.sopt.presentation.main

import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.main.etc.EtcFragment
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.upload.UploadFragment
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.navigateTo

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val homeVm by viewModels<MainViewModel>()

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) {
            //binding.ivProfile.load(it[0])
            //binding.ivProfile2.load(it[1])
            //binding.ivProfile3.load(it[2])
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerBtnv()

        /*
        binding.ivProfile.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }*/

    }

    private fun registerBtnv() {
        binding.btnv.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.btnv_home -> {
                    this.navigateTo<HomeFragment>(fragContainerId = R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.btnv_gallery -> {
                    this.navigateTo<GalleryFragment>(fragContainerId = R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.btnv_upload -> {
                    this.navigateTo<UploadFragment>(fragContainerId = R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                R.id.btnv_etc -> {
                    this.navigateTo<EtcFragment>(fragContainerId = R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
    }
}