package org.android.go.sopt.presentation.login

import android.app.Activity
import android.content.Intent
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

    private var id: String = ""
    private var pw: String = ""
    private var name: String = ""
    private var skill: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            btnLogin.setOnClickListener { onClickLogin() }
            btnJoin.setOnClickListener { onClickSignUp() }
        }

        registerObserver()
    }

    private fun registerObserver() {
        loginVm.loginResult.observe(this) {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            intent.putExtra("id", id)
            intent.putExtra("name", name)
            intent.putExtra("skill", skill)
            startActivity(intent)
            finish()
        }
    }

    private fun onClickLogin() {
        with(binding) {
            if (etId.text.isBlank() || etPw.text.isBlank()) {
                showSnackbar(this.root, "ID 또는 PW를 다시 확인해주세요.")
            } else loginVm.login(applicationContext, etId.text.toString(), etPw.text.toString())
        }
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
            skill = result.data?.getStringExtra("skill") ?: ""
            binding.etId.setText(id)
            binding.etPw.setText(pw)
        }
    }
}