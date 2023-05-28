package org.android.go.sopt.presentation.join

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.android.go.sopt.data.model.ReqSignUpDto
import org.android.go.sopt.data.model.ResSignUpDto
import org.android.go.sopt.data.SrvcPool
import retrofit2.Call
import retrofit2.Response

class JoinViewModel: ViewModel() {
    private val _signUpResult: MutableLiveData<ResSignUpDto> = MutableLiveData()
    val signUpResult: LiveData<ResSignUpDto> = _signUpResult

    private val signUpSrvc = SrvcPool.soptSrvc

    fun signUp(id: String, pw: String, name: String, skill: String) {
        signUpSrvc.signUp(
            ReqSignUpDto(id, pw, name, skill)
        ).enqueue(object: retrofit2.Callback<ResSignUpDto> {
            override fun onResponse(call: Call<ResSignUpDto>, response: Response<ResSignUpDto>) {
                if (response.body()?.status in 200..300) {
                    _signUpResult.value = response.body()
                    /*
                    response.body()?.message.let {
                        Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        goBackToLoginActivity()
                    }
                    */
                }
                else {
                    //Toast.makeText(applicationContext, "서버통신 실패(40X)", Toast.LENGTH_SHORT).show()
                    Log.e("ABC", "서버통신 실패(40X)")
                }
            }

            override fun onFailure(call: Call<ResSignUpDto>, t: Throwable) {
                //Toast.makeText(applicationContext, "서버통신 실패(응답값 X)", Toast.LENGTH_SHORT).show()
                Log.e("ABC", "서버통신 실패(응답값 X)")
            }

        })

    }
}