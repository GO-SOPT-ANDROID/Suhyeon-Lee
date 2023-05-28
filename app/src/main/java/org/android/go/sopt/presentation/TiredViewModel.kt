package org.android.go.sopt.presentation

import org.android.go.sopt.util.ContentUriRequestBody
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TiredViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.soptSrvc

    private val _imgReqBody = MutableLiveData<ContentUriRequestBody>()
    val imgReqBody: LiveData<ContentUriRequestBody>
        get() = _imgReqBody

    fun setRequestBody(requestBody: ContentUriRequestBody) {
        _imgReqBody.value = requestBody
    }

    fun uploadImg() {
        if (_imgReqBody == null) {
            Log.e("ABC", "아직 사진이 등록되지 않았다구")
        } else {
            soptSrvc.uploadImage(_imgReqBody.value!!.toFormData()).enqueue(
                object : Callback<Unit> {
                    override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                        if (response.isSuccessful) {
                            Log.d("ABC", "Your img was successfully uploaded to the server.")
                        }
                    }

                    override fun onFailure(call: Call<Unit>, t: Throwable) {
                        Log.d("ABC", "You've failed to communicate with the server.")
                    }

                }
            )
        }
    }
}