package org.android.go.sopt.Home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.Data.Model.ResUsersDto
import org.android.go.sopt.Data.SrvcPool
import retrofit2.Call
import retrofit2.Response

class HomeViewModel: ViewModel() {
    // 외부에서 liveData 값을 바꾸면 안 돼 -> ViewModel 외부에서는 liveData를 통해 값만 observe(읽기 전용)
    // ViewModel 내부에서는 MutableLiveData를 통해 접근, .value의 값을 변경
    private val _listUsersResult: MutableLiveData<List<ResUsersDto.Data>> = MutableLiveData()
    val listUsersResult: LiveData<List<ResUsersDto.Data>> = _listUsersResult

    private val listUsersSrvc = SrvcPool.reqresSrvc


    fun listUsers() {
        listUsersSrvc.listUsers()
            .enqueue(object: retrofit2.Callback<ResUsersDto> {
                override fun onResponse(call: Call<ResUsersDto>, response: Response<ResUsersDto>) {
                    if (response.isSuccessful) {
                        _listUsersResult.value = response.body()?.data
                    }
                    else { // 서버통신 실패(40X)
                        Log.e("TWOSOME", "서버통신 실패(40X)")
                    }
                }

                override fun onFailure(call: Call<ResUsersDto>, t: Throwable) { // 서버통신 실패(응답값 X)
                    Log.e("TWOSOME", "서버통신 실패(응답값 X)")
                }
            })
    }
}