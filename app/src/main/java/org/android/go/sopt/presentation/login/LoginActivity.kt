package org.android.go.sopt.presentation.login

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.join.JoinActivity
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.showSnackbar

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginVm: LoginViewModel by viewModels()
    private lateinit var sharedPreferences: SharedPreferences

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val id = result.data?.getStringExtra("id") ?: ""
            val pw = result.data?.getStringExtra("pw") ?: ""
            binding.etId.setText(id)
            binding.etPw.setText(pw)
        }
    }

    private var spId: String? = null
    private var spPw: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkAutoLogin()
        with(binding) {
            btnLogin.setOnClickListener { onClickLogin() }
            btnJoin.setOnClickListener { onClickSignUp() }
        }
        registerObserver()
    }

    private fun registerObserver() {
        loginVm.loginResult.observe(this) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("id", it.data.id)
            intent.putExtra("name", it.data.name)
            intent.putExtra("skill", it.data.skill)
            binding.etId.text = null
            binding.etPw.text = null
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            // finish() <- Q. 이렇게 하면 안 되남?
        }
    }

    private fun onClickLogin() {
        with(binding) {
            if (etId.text.isBlank() || etPw.text.isBlank()) {
                showSnackbar(this.root, "ID 또는 PW를 다시 확인해주세요.")
            }
            else {
                spId = binding.etId.text.toString()
                spPw = binding.etPw.text.toString()
                if (binding.cbAutoLogin.isChecked) processAutoLogin()
                loginVm.login(applicationContext, spId!!, spPw!!)
            }
        }
    }

    private fun onClickSignUp() {
        val intent = Intent(this, JoinActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun checkAutoLogin() {
        sharedPreferences = getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        spId = sharedPreferences.getString("id", null)
        spPw = sharedPreferences.getString("pw", null)
        if (spId != null && spPw != null) { // 자동 로그인이 되어 있다
            loginVm.login(applicationContext, spId!!, spPw!!)
        }
    }

    private fun processAutoLogin() {
        val spEditor = sharedPreferences.edit()
        spEditor.putString("id", spId)
        spEditor.putString("pw", spPw)
        spEditor.commit()
    }
}