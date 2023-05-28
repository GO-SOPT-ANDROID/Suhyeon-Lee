package org.android.go.sopt.presentation.join

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityJoinBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.BindingActivity

class JoinActivity : BindingActivity<ActivityJoinBinding>(R.layout.activity_join) {
    //private val signUpSrvc = SrvcPool.soptSrvc
    private val signUpVm by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.btnSignup.setOnClickListener {
            clickSignUpBtn()
        }

        signUpVm.signUpResult.observe(this) {
            Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT).show()
            goBackToLoginActivity()
        }
    }

    private fun clickSignUpBtn() {
        with(binding) {
            if (etId.text.isBlank() || etPw.text.isBlank() ||
                etName.text.isBlank() || etSkill.text.isBlank())
                Snackbar.make(this.root, "입력하지 않은 정보가 있습니다", Snackbar.LENGTH_SHORT).show()
            else if (etId.length() < 6 || 10 < etId.length()) {
                Snackbar.make(this.root, "올바른 아이디를 입력해주십쇼", Snackbar.LENGTH_SHORT).show()
            }
            else if (etPw.length() < 8 || 12 < etPw.length()) {
                Snackbar.make(this.root, "올바른 비밀번호를 입력해주십쇼", Snackbar.LENGTH_SHORT).show()
            }
            else completeSignUp()
        }
    }


    private fun completeSignUp() {
        with(binding) {
            signUpVm.signUp(
                etId.text.toString(), etPw.text.toString(),
                etName.text.toString(), etSkill.text.toString()
            )
        }

        /*
        signUpSrvc.signUp(
            with(binding) {
                ReqSignUpDto(
                    etId.text.toString(), etPw.text.toString(),
                    etName.text.toString(), etSkill.text.toString()
                )
            }
        ).enqueue(object: retrofit2.Callback<ResSignUpDto> {
            override fun onResponse(call: Call<ResSignUpDto>, response: Response<ResSignUpDto>) {
                if (response.body()?.status in 200..300) {
                    response.body()?.message.let {
                        Toast.makeText(applicationContext, "회원가입 성공!", Toast.LENGTH_SHORT).show()
                        goBackToLoginActivity()
                    }
                }
                else {
                    Toast.makeText(applicationContext, "서버통신 실패(40X)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResSignUpDto>, t: Throwable) {
                Toast.makeText(applicationContext, "서버통신 실패(응답값 X)", Toast.LENGTH_SHORT).show()
            }

        })
        */
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