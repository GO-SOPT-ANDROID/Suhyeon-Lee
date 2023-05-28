package org.android.go.sopt

import ContentUriRequestBody
import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import org.android.go.sopt.databinding.ActivityTiredBinding

class TiredActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTiredBinding
    private val tiredVm by viewModels<TiredViewModel>()

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) Log.d("ABC", "승인해주셔서 감사합니다?")
        else Log.d("ABC", "왜 거절하냐 나쁜넘~")
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            binding.iv.load(uri)
            tiredVm.setRequestBody(ContentUriRequestBody(this, uri!!))
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTiredBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnPickImg.setOnClickListener {
            launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
        }

        binding.btnUpload.setOnClickListener {
            tiredVm.uploadImg()
        }

        binding.btnPerm.setOnClickListener {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
}