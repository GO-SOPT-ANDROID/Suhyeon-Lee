package org.android.go.sopt.presentation.join

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ReqJoinDto
import org.android.go.sopt.data.model.ResJoinDto
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast


class JoinViewModel : ViewModel() {
    val id: MutableLiveData<String> = MutableLiveData()
    val idErrMsg: MutableLiveData<String> = MutableLiveData()

    val pw: MutableLiveData<String> = MutableLiveData()
    val pwErrMsg: MutableLiveData<String> = MutableLiveData()

    val nameErrMsg: MutableLiveData<String> = MutableLiveData()
    val skillErrMsg: MutableLiveData<String> = MutableLiveData()

    var isIdValid: Boolean = false
    var isPwValid: Boolean = false

    val isValid: MutableLiveData<Boolean> = MutableLiveData(false)

    private val soptSrvc = SrvcPool.soptSrvc
    private val _joinResult: MutableLiveData<ResJoinDto> = MutableLiveData()
    val joinResult: LiveData<ResJoinDto> = _joinResult

    fun validateInputs() {
        if (isIdValid && isPwValid) {
            isValid.value = true
        }
    }

    fun join(context: Context, id: String, pw: String, name: String, skill: String) {
        soptSrvc.join(ReqJoinDto(id, pw, name, skill)).enqueueUtil(
            { res -> _joinResult.value = res },
            {
                Log.e("ABC", "서버통신 실패(40X)")
                context.showToast("invalid ID: 다른 아이디를 입력해주세요!")
            }
        )
    }
}