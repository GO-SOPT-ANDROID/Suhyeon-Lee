package org.android.go.sopt.presentation.main.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.makeToast

class UploadViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc
    var imgReqBody: ContentUriRequestBody? = null

    fun uploadImg(context: Context, mainVm: MainViewModel) {
        if (imgReqBody == null) Log.e("ABC", "아직 사진이 등록되지 않았다구")
        else {
            soptSrvc.uploadImage(imgReqBody!!.toFormData()).enqueueUtil(
                {
                    context.makeToast("Your img was successfully uploaded to the server.")
                    mainVm.setDialogFlag(false)
                },
                {
                    context.makeToast("You've failed to upload the img to the server.")
                    mainVm.setDialogFlag(false)
                }
            )
        }
    }
}