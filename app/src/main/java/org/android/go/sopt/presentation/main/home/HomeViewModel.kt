package org.android.go.sopt.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.util.enqueueUtil

class HomeViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.reqresSrvc

    // 외부에서 liveData 값을 바꾸면 안 돼 -> ViewModel 외부에서는 liveData를 통해 값만 observe(읽기 전용)
    // ViewModel 내부에서는 MutableLiveData를 통해 접근, .value의 값을 변경
    private val _listUsersResult: MutableLiveData<List<ResUsersDto.Data>> = MutableLiveData()
    val listUsersResult: LiveData<List<ResUsersDto.Data>> = _listUsersResult

    // true -> show dialog, false -> close dialog
    private val _dialogFlag: MutableLiveData<Boolean> = MutableLiveData(false)
    val dialogFlag: LiveData<Boolean> = _dialogFlag

    fun listUsers() {
        soptSrvc.listUsers().enqueueUtil(
            { res ->
                _listUsersResult.value = res.data
                _dialogFlag.value = false
            },
            { Log.e("ABC", "서버통신 실패(40X)") }
        )
    }

    fun setDialogFlag(b: Boolean) {
        _dialogFlag.value = b
    }
}