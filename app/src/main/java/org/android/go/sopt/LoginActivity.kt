package org.android.go.sopt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    private var id: String = ""
    private var pw: String = ""
    private var name: String = "홍길동"
    private var hobby: String = "숨 쉬기"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onClickLogin()
        onClickSignup()
    }

    private fun onClickLogin() {
        with(binding) {
            btnLogin.setOnClickListener {
                if (etId.length() < 6 || 10 < etId.length() ||
                    etPw.length() < 8 || 12 < etPw.length()) {
                    Snackbar.make(it, "invalid", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this@LoginActivity, MyInfoActivity::class.java)
                    intent.putExtra("name", name)
                    intent.putExtra("hobby", hobby)
                    Toast.makeText(applicationContext, "로그인 성공!", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                }

            }
        }
    }

    private fun onClickSignup() {
        binding.btnSignup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            resultLauncher.launch(intent)
        }
    }

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            id = result.data?.getStringExtra("id") ?: ""
            pw = result.data?.getStringExtra("pw") ?: ""
            name = result.data?.getStringExtra("name") ?: ""
            hobby = result.data?.getStringExtra("hobby") ?: ""
            binding.etId.setText(id)
            binding.etPw.setText(pw)
            Snackbar.make(binding.root, "회원가입 성공!", Snackbar.LENGTH_SHORT).show()
        }
    }
}