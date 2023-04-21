package org.android.go.sopt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import org.android.go.sopt.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btnSignup.setOnClickListener {
                if (etId.length() == 0 || etPw.length() == 0 ||
                    etName.length() == 0 || etHobby.length() == 0)
                    Snackbar.make(it, "입력하지 않은 정보가 있습니다", Snackbar.LENGTH_SHORT).show()
                else if (etId.length() < 6 || 10 < etId.length()) {
                    Snackbar.make(it, "올바른 아이디를 입력해주십쇼", Snackbar.LENGTH_SHORT).show()
                }
                else if (etPw.length() < 8 || 12 < etPw.length()) {
                    Snackbar.make(it, "올바른 비밀번호를 입력해주십쇼", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    intent.putExtra("id", etId.text.toString())
                    intent.putExtra("pw", etPw.text.toString())
                    intent.putExtra("name", etName.text.toString())
                    intent.putExtra("hobby", etHobby.text.toString())
                    setResult(RESULT_OK, intent)
                    finish()
                }
            }
        }
    }
}