package org.android.go.sopt.util

import android.util.Log
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 서버 통신
fun <T> Call<T>.enqueueUtil(
    onSuccess: (T) -> Unit,
    onError: ((stateCode: Int) -> Unit)? = null
) {
    this.enqueue(
        object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if (response.isSuccessful) {
                    onSuccess.invoke(response.body() ?: return)
                } else {
                    onError?.invoke(response.code())
                }
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e("ABC", "[통신 오류] error:$t")
            }
        }
    )
}

// activity에서 fragment 바꾸기
inline fun <reified T : Fragment> AppCompatActivity.navigateTo(
    @IdRes fragContainerId: Int,
    tag: String? = null,
    action: () -> Unit = {}
) {
    supportFragmentManager.commit {
        replace<T>(fragContainerId, tag)
        action()
        setReorderingAllowed(true)
    }
}