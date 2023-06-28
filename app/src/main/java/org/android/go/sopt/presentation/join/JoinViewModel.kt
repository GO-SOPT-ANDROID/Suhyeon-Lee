package org.android.go.sopt.presentation.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ReqJoinDto
import org.android.go.sopt.data.model.ResJoinDto
import org.android.go.sopt.util.ResultConstants

class JoinViewModel : ViewModel() {
    // input (EditText)
    val id: MutableLiveData<String> = MutableLiveData()
    val pw: MutableLiveData<String> = MutableLiveData()
    val name: MutableLiveData<String> = MutableLiveData()
    val skill: MutableLiveData<String> = MutableLiveData()

    // input for BindingAdapter (error), validate input
    var isIdValid: LiveData<Int> = id.switchMap { MutableLiveData(it.validateId()) }
    var isPwValid: LiveData<Int> = pw.switchMap { MutableLiveData(it.validatePw()) }
    var isNameValid: LiveData<Int> = name.switchMap { MutableLiveData(it.validateEmpty()) }
    var isSkillValid: LiveData<Int> = skill.switchMap { MutableLiveData(it.validateEmpty()) }

    // activate button
    var isValid: MediatorLiveData<Boolean> = MediatorLiveData(false)

    private val soptSrvc = SrvcPool.soptSrvc
    private val _joinResult: MutableLiveData<ResJoinDto> = MutableLiveData()
    val joinResult: LiveData<ResJoinDto> = _joinResult

    init {
        with (isValid) {
            addSource(isIdValid) { isValid.value = _isValid() }
            addSource(isPwValid) { isValid.value = _isValid() }
        }
    }

    private fun _isValid(): Boolean {
        return if (isIdValid.value == null) false
        else if (isPwValid.value == null) false
        else isIdValid.value!! >= ResultConstants.WRONG && isPwValid.value!! >= ResultConstants.WRONG
    }
    fun validateGoingNext(): Boolean {
        return if (isValid.value == false) false
        else if (isNameValid.value == null || isSkillValid.value == null) false
        else if (isNameValid.value!! <= ResultConstants.INVALID) false
        else { // input이 모두 valid -> 회원가입 가능
            join()
            isSkillValid.value!! >= ResultConstants.WRONG
        }
    }

    private fun join() {
        viewModelScope.launch {
            kotlin.runCatching {
                soptSrvc.join(
                    ReqJoinDto(id.value!!, pw.value!!, name.value!!, skill.value!!)
                )
            }.fold(
                onSuccess = { response ->
                    _joinResult.value = response
                }, onFailure = {
                    _joinResult.value = ResJoinDto()
                    Log.e("ABC", "회원가입 서버통신 이상! ${it}")
                }
            )
        }
    }
}