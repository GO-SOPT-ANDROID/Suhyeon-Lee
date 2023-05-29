package org.android.go.sopt.presentation.join

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.databinding.ActivityJoinBinding
import org.android.go.sopt.presentation.login.LoginActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.showSnackbar
import org.android.go.sopt.util.showToast

class JoinActivity : BindingActivity<ActivityJoinBinding>(R.layout.activity_join) {
    private val joinVm by viewModels<JoinViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        registerClickEvents()
        registerObserver()
    }

    private fun registerClickEvents() {
        binding.btnJoin.setOnClickListener {
            clickJoinBtn()
        }
    }

    private fun registerObserver() {
        joinVm.joinResult.observe(this) {
            this.showToast("회원가입 성공!")
            goBackToLoginActivity()
        }

        binding.vm = joinVm
        joinVm.id.observe(this) {
            joinVm.isIdValid.postValue(it.validateId())
        }
        joinVm.pw.observe(this) {
            joinVm.isPwValid.postValue(it.validatePw())
        }
        joinVm.isIdValid.observe(this) {
            joinVm.validateInputs()
        }
        joinVm.isPwValid.observe(this) {
            joinVm.validateInputs()
        }
    }

    private fun checkOptionalInput(): Boolean {
        return if (binding.etName.text.isNullOrEmpty()) false
        else if (binding.etSkill.text.isNullOrEmpty()) false
        else true
    }

    private fun clickJoinBtn() {
        with(binding) {
            if (!etName.text.isNullOrEmpty()) { // valid
                joinVm.nameErrMsg.postValue("")
                if (!etSkill.text.isNullOrEmpty()) { // valid
                    joinVm.skillErrMsg.postValue("")
                    completeJoin()
                }
                else joinVm.skillErrMsg.postValue("특기를 알려주세요!")
            }
            else joinVm.nameErrMsg.postValue("이름을 알려주세요!")
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
            intent.putExtra("name", etName.text.toString())
            intent.putExtra("skill", etSkill.text.toString())
        }
        setResult(RESULT_OK, intent)
        finish()
    }
}