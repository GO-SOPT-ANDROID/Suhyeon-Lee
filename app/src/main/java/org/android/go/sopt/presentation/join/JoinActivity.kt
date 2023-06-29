package org.android.go.sopt.presentation.join

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.databinding.ActivityJoinBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.ResultConstants
import org.android.go.sopt.util.hideKeyboard
import org.android.go.sopt.util.showToast

class JoinActivity : BindingActivity<ActivityJoinBinding>(R.layout.activity_join) {
    private val joinVm by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerClickEvents()
        registerObserver()
    }

    private fun registerClickEvents() {
        with(binding) {
            clParent.setOnTouchListener { _, event ->
                hideKeyboard(this@JoinActivity)
                return@setOnTouchListener false
            }
            btnJoin.setOnClickListener {
                if (!joinVm.validateGoingNext()) {
                    this@JoinActivity.showToast("유효하지 않은 값이 있습니다.")
                }
            }
        }
    }

    private fun registerObserver() {
        binding.vm = joinVm
        joinVm.joinResult.observe(this@JoinActivity) {
            if (it.status == ResultConstants.WRONG)
                this@JoinActivity.showToast("invalid ID: 다른 아이디를 입력해주세요!")
            else {
                this@JoinActivity.showToast("회원가입 성공!")
                goBackToLoginActivity()
            }
        }
    }

    private fun goBackToLoginActivity() {
        val intent = Intent(this@JoinActivity, LoginActivity::class.java).apply {
            putExtra("data", ReqLogInDto(joinVm.id.value!!, joinVm.pw.value!!))
        }
        /*
        with(binding) {
            intent.putExtra("id", etId.text.toString())
            intent.putExtra("pw", etPw.text.toString())
        }*/
        setResult(RESULT_OK, intent)
        finish()
    }
}