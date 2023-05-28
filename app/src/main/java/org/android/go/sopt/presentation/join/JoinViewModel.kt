package org.android.go.sopt.presentation.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.ReqJoinDto
import org.android.go.sopt.data.model.ResJoinDto
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.util.enqueueUtil

class JoinViewModel: ViewModel() {
    private val _joinResult: MutableLiveData<ResJoinDto> = MutableLiveData()
    val joinResult: LiveData<ResJoinDto> = _joinResult

    private val soptSrvc = SrvcPool.soptSrvc

    fun join(id: String, pw: String, name: String, skill: String) {
        soptSrvc.signUp(ReqJoinDto(id, pw, name, skill)).enqueueUtil(
            { res -> _joinResult.value = res },
            { Log.e("ABC", "서버통신 실패(40X)") }
        )
    }
}