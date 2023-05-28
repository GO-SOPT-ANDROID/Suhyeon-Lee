package org.android.go.sopt.presentation.join

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityJoinBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.showSnackbar
import org.android.go.sopt.util.showToast

class JoinActivity : BindingActivity<ActivityJoinBinding>(R.layout.activity_join) {
    private val signUpVm by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerClickEvents()
    }

    private fun registerClickEvents() {
        binding.btnSignup.setOnClickListener {
            clickSignUpBtn()
        }

        signUpVm.joinResult.observe(this) {
            this.showToast("회원가입 성공!")
            goBackToLoginActivity()
        }
    }

    private fun clickSignUpBtn() {
        with(binding) {
            if (etId.text.isBlank()) showSnackbar(this.root, "ID를 입력하세요.")
            else if (etPw.text.isBlank()) showSnackbar(this.root, "PW를 입력하세요.")
            else if (etName.text.isBlank()) showSnackbar(this.root, "이름을 입력하세요.")
            else if (etSkill.text.isBlank()) showSnackbar(this.root, "특기를 입력하세요.")
            else if (etId.length() < 6 || 10 < etId.length()) {
                showSnackbar(this.root, "ID는 6~10자여야 합니다.")
            } else if (etPw.length() < 8 || 12 < etPw.length()) {
                showSnackbar(this.root, "PW는 8~12자여야 합니다.")
            } else completeSignUp()
        }
    }


    private fun completeSignUp() {
        with(binding) {
            signUpVm.join(
                applicationContext,
                etId.text.toString(), etPw.text.toString(),
                etName.text.toString(), etSkill.text.toString()
            )
        }
    }

    private fun goBackToLoginActivity() {
        val intent = Intent(this@JoinActivity, LoginActivity::class.java)
        with(binding) {
            intent.putExtra("id", etId.text.toString())
            intent.putExtra("pw", etPw.text.toString())
            intent.putExtra("name", etName.text.toString())
            intent.putExtra("skill", etSkill.text.toString())
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}