package org.android.go.sopt.presentation.main.upload

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import coil.load
import org.android.go.sopt.R
import org.android.go.sopt.databinding.FragmentUploadBinding
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.BindingFragment
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.showToast

class UploadFragment : BindingFragment<FragmentUploadBinding>(R.layout.fragment_upload) {

    private val mainVm: MainViewModel by activityViewModels()
    private val uploadVm: UploadViewModel by viewModels()

    private val getSeveralImgsLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) {
            uploadVm.selectPhoto(it)
        }

    private val permLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            requireContext().showToast("Permission allowed!")
        } else {
            requireContext().showToast("Permission denied!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = uploadVm
        registerClickEvents()
        registerObservers()
    }

    private fun registerClickEvents() {
        binding.btnSelectPhoto.setOnClickListener {
            getSeveralImgsLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }

        binding.btnUpload.setOnClickListener {
            if (uploadVm.title.value.isNullOrEmpty() || uploadVm.singer.value.isNullOrEmpty())
                requireContext().showToast("Please fill in the empty blanks.")
            else {
                mainVm.setDialogFlag(true)
                uploadVm.uploadMusic(requireContext(), mainVm)
            }
        }

        binding.btnReqPerm.setOnClickListener {
            permLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    private fun registerObservers() {
        uploadVm.imgReqBodyList.observe(viewLifecycleOwner) {
            for (i in 0 until 3) {
                val childView = binding.llImgs.getChildAt(i)
                if (childView is ImageView) {
                    if (i < it.size) childView.load(it[i])
                    else childView.setImageResource(R.color.mint_10)
                }
            }
        }
    }
}