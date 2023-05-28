package org.android.go.sopt.presentation.main.upload

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class UploadViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc
    var imgReqBodys: MutableList<ContentUriRequestBody> = mutableListOf()

    fun uploadImg(context: Context, mainVm: MainViewModel) {
        if (imgReqBodys.size == 0)
            context.showToast("Please select images first.")
        else {
            var resSucCnt = 0 // successful response의 개수
            for (i in 0 until imgReqBodys.size) {
                soptSrvc.uploadImage(imgReqBodys[i].toFormData()).enqueueUtil(
                    {
                        Log.d("ABC", "Img ${i + 1} is successfully uploaded to the server.")
                        resSucCnt++
                        if (resSucCnt == imgReqBodys.size - 1) {
                            context.showToast("Imgs are successfully uploaded to the server.")
                            mainVm.setDialogFlag(false)
                        }
                    },
                    {
                        context.showToast("You've failed to upload ${i+1}th img")
                    }
                )
            }
        }
    }
}