package org.android.go.sopt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.Data.Model.ReqLogInDto
import org.android.go.sopt.Data.Model.ReqSignUpDto
import org.android.go.sopt.Data.Model.ResLogInDto
import org.android.go.sopt.Data.Model.ResSignUpDto
import org.android.go.sopt.Data.SrvcPool
import org.android.go.sopt.Home.HomeActivity
import org.android.go.sopt.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val logInSrvc = SrvcPool.soptSrvc

    private var id: String = ""
    private var pw: String = ""
    private var name: String = ""
    private var skill: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnLogin.setOnClickListener { onClickLogin() }
            btnSignup.setOnClickListener { onClickSignUp()}
        }
    }

    private fun onClickLogin() {
        with(binding) {
            if (etId.text.isBlank() || etPw.text.isBlank()) {
                Snackbar.make(this.root, "Invalid ID or Password", Snackbar.LENGTH_SHORT).show()
            }
            else completeLogIn()
        }
    }

    private fun completeLogIn() {
        logInSrvc.logIn(
            with(binding) {
                ReqLogInDto(
                    etId.text.toString(), etPw.text.toString()
                )
            }
        ).enqueue(object: retrofit2.Callback<ResLogInDto> {
            override fun onResponse(call: Call<ResLogInDto>, response: Response<ResLogInDto>) {
                if (response.body()?.status in 200..300) {
                    response.body()?.message.let { // 서버통신 성공
                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
                else { // 서버통신 실패(40X)
                    Toast.makeText(applicationContext, "서버통신 실패(40X)", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResLogInDto>, t: Throwable) { // 서버통신 실패(응답값 X)
                Toast.makeText(applicationContext, "서버통신 실패(응답값 X)", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun onClickSignUp() {
        val intent = Intent(this, SignupActivity::class.java)
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