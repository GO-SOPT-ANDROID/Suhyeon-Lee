package org.android.go.sopt.presentation.login

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import org.android.go.sopt.R
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.databinding.ActivityLoginBinding
import org.android.go.sopt.presentation.join.JoinActivity
import org.android.go.sopt.presentation.main.MainActivity
import org.android.go.sopt.util.BindingActivity
import org.android.go.sopt.util.ResultConstants
import org.android.go.sopt.util.UserConstants
import org.android.go.sopt.util.parcelable
import org.android.go.sopt.util.showLoadingDialog
import org.android.go.sopt.util.showSnackbar
import org.android.go.sopt.util.showToast

class LoginActivity : BindingActivity<ActivityLoginBinding>(R.layout.activity_login) {
    private val loginVm: LoginViewModel by viewModels()
    private var savedId: String? = null
    private var savedPw: String? = null
    private lateinit var dialog: Dialog

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val userData = result.data ?: return@registerForActivityResult
            val userInfo = userData.parcelable("data") ?: ReqLogInDto()
            loginVm.setIdAndPw(userInfo.id, userInfo.password)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        registerClickEvents()
        registerObserver()
    }

    private fun initUi() {
        dialog = Dialog(this)
        loginVm.setDialogFlag(true)
        binding.vm = loginVm
        checkAutoLogin()
    }

    private fun registerClickEvents() {
        with(binding) {
            btnLogin.setOnClickListener { loginVm.login() }
            btnJoin.setOnClickListener { onClickSignUp() }
        }
    }

    private fun registerObserver() {
        loginVm.loginResult.observe(this) {
            loginVm.setDialogFlag(false)
            when (it) {
                ResultConstants.INVALID -> showSnackbar(binding.root, "ID 또는 PW가 비어있습니다.")
                ResultConstants.WRONG -> this.showToast("로그인 정보가 존재하지 않습니다. 회원가입을 해주세요.")
            }
        }

        loginVm.loginResponse.observe(this) {
            if ((savedId == null || savedPw == null) && binding.cbAutoLogin.isChecked)
                UserConstants.setAutoLogin(loginVm.id.value, loginVm.pw.value)
            loginVm.setDialogFlag(false)
            val intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
            // finish() <- Q. 이렇게 하면 안 되남?
        }

        loginVm.dialogFlag.observe(this) {
            if (it) this.showLoadingDialog(dialog, "Checking auto login...")
            else dialog.cancel()
        }
    }

    private fun onClickSignUp() {
        val intent = Intent(this, JoinActivity::class.java)
        resultLauncher.launch(intent)
    }

    private fun checkAutoLogin() {
        UserConstants.sharedPreferences =
            getSharedPreferences("sharedPreferences", Activity.MODE_PRIVATE)
        savedId = UserConstants.getSharedPref("id")
        savedPw = UserConstants.getSharedPref("pw")

        if (savedId != null && savedPw != null) { // 자동 로그인이 되어 있다
            with(loginVm) {
                binding.cbAutoLogin.isChecked = true
                setIdAndPw(savedId!!, savedPw!!)
                UserConstants.savedId = savedId as String
                login()
            }
        }
        else loginVm.setDialogFlag(false)
    }
}