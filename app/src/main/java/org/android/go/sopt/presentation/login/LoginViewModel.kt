package org.android.go.sopt.presentation.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.data.model.ResLogInDto
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class LoginViewModel : ViewModel() {
    private val _loginResult: MutableLiveData<ResLogInDto> = MutableLiveData()
    val loginResult: LiveData<ResLogInDto> = _loginResult

    private val soptSrvc = SrvcPool.soptSrvc

    fun login(context: Context, id: String, pw: String) {
        soptSrvc.logIn(ReqLogInDto(id, pw)).enqueueUtil(
            { res -> _loginResult.value = res },
            { context.showToast("[login] 로그인 정보가 존재하지 않습니다. 회원가입을 해주세요.") }
        )
    }
}