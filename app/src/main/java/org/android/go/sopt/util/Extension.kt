package org.android.go.sopt.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.DialogLoadingBinding
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

fun Activity.showLoadingDialog(dialog: Dialog, s: String) {
    val binding = DialogLoadingBinding.inflate(layoutInflater)
    dialog.setContentView(binding.root)
    dialog.setCancelable(false)
    binding.tvContent.text = s

    val params: WindowManager.LayoutParams = dialog.window?.attributes!!
    params.width = WindowManager.LayoutParams.MATCH_PARENT
    params.height = WindowManager.LayoutParams.WRAP_CONTENT
    dialog.window?.attributes = params

    dialog.show()
}

fun Context.showToast(msg: String) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}

fun showSnackbar(v: View, s: String) {
    Snackbar.make(v, s, Snackbar.LENGTH_SHORT).show()
}

fun hideKeyboard(activity: Activity) {
    val inputManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(
        activity.getCurrentFocus()?.getWindowToken() ?: null,
        InputMethodManager.HIDE_NOT_ALWAYS
    )
}