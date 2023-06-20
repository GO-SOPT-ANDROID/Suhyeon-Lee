package org.android.go.sopt.presentation.main.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ResUsersDto
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.main.MainViewModel
import org.android.go.sopt.util.enqueueUtil

class HomeViewModel : ViewModel() {
    private val soptSrvc = SrvcPool.reqresSrvc

    // 외부에서 liveData 값을 바꾸면 안 돼 -> ViewModel 외부에서는 liveData를 통해 값만 observe(읽기 전용)
    // ViewModel 내부에서는 MutableLiveData를 통해 접근, .value의 값을 변경
    private val _listUsersResult: MutableLiveData<List<ResUsersDto.Data>> = MutableLiveData()
    val listUsersResult: LiveData<List<ResUsersDto.Data>> = _listUsersResult

    val btnDelete: MutableLiveData<Boolean> = MutableLiveData(false)

    fun listUsers(mainVm: MainViewModel) {
        soptSrvc.listUsers().enqueueUtil(
            { res ->
                _listUsersResult.value = res.data
                mainVm.setDialogFlag(false)
            },
            { Log.e("ABC", "서버통신 실패(40X)") }
        )
    }

    fun clickBtnDelete() { // inverter
        btnDelete.value = btnDelete.value != true
    }
}