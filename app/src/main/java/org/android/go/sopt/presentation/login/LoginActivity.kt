package org.android.go.sopt.presentation.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.R
import org.android.go.sopt.data.SrvcPool
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.data.model.ResLogInDto
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.presentation.join.JoinActivity
import org.android.go.sopt.util.BindingActivity
import retrofit2.Call
import retrofit2.Response

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val logInSrvc = SrvcPool.soptSrvc

    private var id: String = ""
    private var pw: String = ""
    private var name: String = ""
    private var skill: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnLogin.setOnClickListener { onClickLogin() }
            btnSignup.setOnClickListener { onClickSignUp() }
        }
    }

    private fun onClickLogin() {
        with(binding) {
            if (etId.text.isBlank() || etPw.text.isBlank()) {
                Snackbar.make(this.root, "Invalid ID or Password", Snackbar.LENGTH_SHORT).show()
            } else completeLogIn()
        }
    }

    private fun completeLogIn() {
        logInSrvc.logIn(
            with(binding) {
                ReqLogInDto(
                    etId.text.toString(), etPw.text.toString()
                )
            }
        ).enqueue(object : retrofit2.Callback<ResLogInDto> {
            override fun onResponse(call: Call<ResLogInDto>, response: Response<ResLogInDto>) {
                if (response.body()?.status in 200..300) {
                    response.body()?.message.let { // 서버통신 성공
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                } else { // 서버통신 실패(40X)
                    Toast.makeText(applicationContext, "서버통신 실패(40X)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResLogInDto>, t: Throwable) { // 서버통신 실패(응답값 X)
                Toast.makeText(applicationContext, "서버통신 실패(응답값 X)", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onClickSignUp() {
        val intent = Intent(this, JoinActivity::class.java)
        resultLauncher.launch(intent)
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            id = result.data?.getStringExtra("id") ?: ""
            pw = result.data?.getStringExtra("pw") ?: ""
            name = result.data?.getStringExtra("name") ?: ""
            skill = result.data?.getStringExtra("hobby") ?: ""
            binding.etId.setText(id)
            binding.etPw.setText(pw)
        }
    }
}