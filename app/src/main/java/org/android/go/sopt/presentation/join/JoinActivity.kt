package org.android.go.sopt.presentation.join

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityJoinBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.BindingActivity
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
        binding.clParent.setOnTouchListener { v, event ->
            hideKeyboard(this)
            return@setOnTouchListener false
        }
        binding.btnJoin.setOnClickListener {
            joinVm.validateGoingNext()
        }
    }

    private fun registerObserver() {
        binding.vm = joinVm
        with(joinVm) {
            // validate inputs
            id.observe(this@JoinActivity) { isIdValid.postValue(it.validateId()) }
            pw.observe(this@JoinActivity) { isPwValid.postValue(it.validatePw()) }
            name.observe(this@JoinActivity) { isNameValid.postValue(it.validateEmpty()) }
            skill.observe(this@JoinActivity) { isSkillValid.postValue(it.validateEmpty()) }

            // validate button enableness
            isIdValid.observe(this@JoinActivity) {
                Log.e("ABC", "[observer] isIdValid : $it")
                validateInputs() }
            isPwValid.observe(this@JoinActivity) {
                Log.e("ABC", "[observer] isPwValid : $it")
                validateInputs() }

            // validate moving to the next page
            goNext.observe(this@JoinActivity) {
                if (it == true) completeJoin()
                else {
                    this@JoinActivity.showToast("유효하지 않은 값이 있습니다.")
                }
            }

            joinResult.observe(this@JoinActivity) {
                this@JoinActivity.showToast("회원가입 성공!")
                goBackToLoginActivity()
            }
        }
    }

    private fun completeJoin() {
        with(binding) {
            joinVm.join(
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
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}