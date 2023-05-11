package org.android.go.sopt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.Home.HomeActivity
import org.android.go.sopt.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private var id: String = ""
    private var pw: String = ""
    private var name: String = "홍길동"
    private var skill: String = "숨 쉬기"

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
            if (etId.length() < 6 || 10 < etId.length() ||
                etPw.length() < 8 || 12 < etPw.length()) {
                Snackbar.make(this.root, "Invalid ID or Password", Snackbar.LENGTH_SHORT).show()
            }
            else {
                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }
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