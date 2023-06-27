package org.android.go.sopt.presentation.main.upload

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.ContentUriRequestBody
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class UploadViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc
    var imgReqBodyList: MutableLiveData<List<Uri>> = MutableLiveData(listOf())
    val title: MutableLiveData<String> = MutableLiveData()
    val singer: MutableLiveData<String> = MutableLiveData()

    fun uploadMusic(context: Context, mainVm: MainViewModel) {
        if (imgReqBodyList.value?.size == 0)
            context.showToast("Please select images first.")
        else {
            var resSucCnt = 0 // successful response의 개수
            val dataHashMap = hashMapOf(
                "title" to title.value.toPlainRequestBody(),
                "singer" to singer.value.toPlainRequestBody()
            )

            for (i in 0 until imgReqBodyList.value!!.size) {
                viewModelScope.launch {
                    kotlin.runCatching {
                        soptSrvc.uploadMusic(mainVm.id,
                            ContentUriRequestBody(context, (imgReqBodyList.value)!![i]).toFormData(),
                            dataHashMap)
                    }.fold(
                        onSuccess = { response ->
                            resSucCnt++
                            if (resSucCnt == imgReqBodyList.value!!.size) {
                                initializeUi()
                                context.showToast(response.message)
                                mainVm.setDialogFlag(false)
                            }
                        }, onFailure = {
                            context.showToast("You've failed to upload ${i}th music.")
                        }
                    )
                }
            }
        }
    }

    private fun initializeUi() {
        title.value = ""
        singer.value = ""
        imgReqBodyList.value = emptyList()
    }

    // imgReqBodyList에 선택한 photo를 추가한다
    fun selectPhoto(list: List<Uri>) {
        if (list.isNotEmpty()) {
            val newList = mutableListOf<Uri>()
            val curSize = imgReqBodyList.value?.size ?: 0
            (curSize + list.size).let {
                if (it <= 3) imgReqBodyList.value?.let { prevList -> newList.addAll(prevList) }
                newList.addAll(list)
                imgReqBodyList.value = newList
            }
        }
    }

    private fun String?.toPlainRequestBody() =
        requireNotNull(this).toRequestBody("text/plain".toMediaTypeOrNull())
}