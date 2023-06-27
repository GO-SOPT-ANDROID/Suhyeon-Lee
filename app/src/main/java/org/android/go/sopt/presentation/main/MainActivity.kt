package org.android.go.sopt.presentation.main

import android.app.Dialog
import android.os.Bundle
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityMainBinding
import org.android.go.sopt.presentation.main.music.MusicFragment
import org.android.go.sopt.presentation.main.gallery.GalleryFragment
import org.android.go.sopt.presentation.main.home.HomeFragment
import org.android.go.sopt.presentation.main.upload.UploadFragment
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.navigateTo
import org.android.go.sopt.util.showLoadingDialog

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private val mainVm by viewModels<MainViewModel>()
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getDataFromLogin()
        registerBtnv()
        registerObserver()
        dialog = Dialog(this)
    }

    private fun getDataFromLogin() {
        with(mainVm) {
            id = intent.getStringExtra("id") ?: "unknown"
            name = intent.getStringExtra("name") ?: "unknown"
            skill = intent.getStringExtra("skill") ?: "unknown"
        }
    }

    private fun registerObserver() {
        mainVm.dialogFlag.observe(this) {
            if (it) this.showLoadingDialog(dialog, "Loading data...")
            else dialog.cancel()
        }
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
                R.id.btnv_music -> {
                    this.navigateTo<MusicFragment>(fragContainerId = R.id.main_fragment_container)
                    return@setOnItemSelectedListener true
                }
                else -> return@setOnItemSelectedListener false
            }
        }
        binding.btnv.selectedItemId = R.id.btnv_home
    }
}