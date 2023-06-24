package org.android.go.sopt.presentation.main.upload

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class UploadViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc
    var imgReqBodys: MutableList<ContentUriRequestBody> = mutableListOf()

    var imgReqBodyList: MutableLiveData<MutableList<Uri>> = MutableLiveData(mutableListOf())
    val title: MutableLiveData<String> = MutableLiveData()
    val singer: MutableLiveData<String> = MutableLiveData()

    // [세미나7] multipart 사진 전송
    fun uploadImg(context: Context, mainVm: MainViewModel) {
        if (imgReqBodys.size == 0)
            context.showToast("Please select images first.")
        else {
            var resSucCnt = 0 // successful response의 개수
            for (i in 0 until imgReqBodys.size) {
                soptSrvc.uploadImage(imgReqBodys[i].toFormData()).enqueueUtil(
                    {
                        Log.d("ABC", "Img $i is successfully uploaded to the server.")
                        resSucCnt++
                        if (resSucCnt == imgReqBodys.size - 1) {
                            context.showToast("Imgs are successfully uploaded to the server.")
                            mainVm.setDialogFlag(false)
                        }
                    },
                    {
                        context.showToast("You've failed to upload ${i}th img")
                    }
                )
            }
        }
    }

    fun uploadMusic(context: Context, mainVm: MainViewModel) {
        if (imgReqBodys.size == 0)
            context.showToast("Please select images first.")
        else {
            var resSucCnt = 0 // successful response의 개수
            val dataHashMap = hashMapOf(
                "title" to title.value.toPlainRequestBody(),
                "singer" to singer.value.toPlainRequestBody()
            )

            for (i in 0 until imgReqBodys.size) {
                soptSrvc.uploadMusic(
                    mainVm.id,
                    imgReqBodys[i].toFormData(),
                    dataHashMap
                ).enqueueUtil(
                    {
                        resSucCnt++
                        if (resSucCnt == imgReqBodys.size) {
                            initializeUi()
                            context.showToast(it.message)
                            mainVm.setDialogFlag(false)
                        }
                    },
                    {
                        context.showToast("You've failed to upload ${i}th music.")
                    }
                )
            }
        }
    }

    private fun initializeUi() {
        title.value = ""
        singer.value = ""
        imgReqBodys.clear()
    }

    // imgReqBodyList에 선택한 photo를 추가한다
    fun selectPhoto(list: List<Uri>) {
        if (list.isNotEmpty()) {
            val curSize = imgReqBodyList.value?.size ?: 0
            (curSize + list.size).let {
                when {
                    it <= 3 -> {

                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
}