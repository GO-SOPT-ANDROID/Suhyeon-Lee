package org.android.go.sopt.presentation.login

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.data.model.ResLogInDto
import org.android.go.sopt.util.InputResult
import org.android.go.sopt.util.UserConstants
import org.android.go.sopt.util.enqueueUtil
import org.android.go.sopt.util.showToast

class LoginViewModel : ViewModel() {
    private val _loginResponse: MutableLiveData<ResLogInDto.SignUpData> = MutableLiveData()
    val loginResponse: LiveData<ResLogInDto.SignUpData> = _loginResponse

    private val _loginResult: MutableLiveData<Int> = MutableLiveData() // invalid, valid&wrong, valid&right
    val loginResult: LiveData<Int> = _loginResult

    private val soptSrvc = SrvcPool.soptSrvc

    // true -> show dialog, false -> close dialog
    private val _dialogFlag: MutableLiveData<Boolean> = MutableLiveData(false)
    val dialogFlag: LiveData<Boolean> = _dialogFlag

    val id: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")

    fun login() {
        if (id.value.isNullOrEmpty() || pw.value.isNullOrEmpty())
            _loginResult.value = InputResult.INVALID
        else {
            soptSrvc.logIn(ReqLogInDto(id.value!!, pw.value!!)).enqueueUtil({ response ->
                UserConstants.id = response.data.id
                UserConstants.name = response.data.name
                UserConstants.skill = response.data.skill
                _loginResponse.value = response.data
            }, {
                _loginResult.value = InputResult.WRONG
            })
        }
    }

    fun setDialogFlag(b: Boolean) { _dialogFlag.value = b }
    fun setIdEt(s: String) { id.value = s }
    fun setPwEt(s: String) { pw.value = s }
}