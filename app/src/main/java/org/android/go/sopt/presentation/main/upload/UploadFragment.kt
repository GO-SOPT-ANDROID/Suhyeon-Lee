package org.android.go.sopt.presentation.main.upload

import android.Manifest
import android.os.Build
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
import org.android.go.sopt.util.makeToast

class UploadFragment : BindingFragment<FragmentUploadBinding>(R.layout.fragment_upload) {

    private val mainVm: MainViewModel by activityViewModels()
    private val uploadVm: UploadViewModel by viewModels()

    private val getSeveralImgsLauncher =
        registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 3)) {
            for (i in 0..it.size - 1) {
                val childView = binding.llImgs.getChildAt(i)
                if (childView is ImageView) {
                    Log.d("ABC", "${it[i]} in i-th image view!")
                    childView.load(it[i])
                }

                uploadVm.imgReqBodys.add(ContentUriRequestBody(requireContext(), it[i]))
            }
        }

    private val permLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                requireContext().makeToast("Permission allowed!")
            } else {
                requireContext().makeToast("Permission denied!")
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerClickEvents()
    }

    private fun registerClickEvents() {
        binding.btnSelectPhoto.setOnClickListener {
            getSeveralImgsLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo)
            )
        }

        binding.btnUpload.setOnClickListener {
            mainVm.setDialogFlag(true)
            uploadVm.uploadImg(requireContext(), mainVm)
        }

        binding.btnReqPerm.setOnClickListener {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)
            ) {
                requireContext().makeToast("Please allow us!")
            }
            else { // ask for permission
                permLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
}