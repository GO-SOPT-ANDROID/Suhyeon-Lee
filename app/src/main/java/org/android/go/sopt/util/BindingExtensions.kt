package org.android.go.sopt.util

import android.content.res.ColorStateList
import androidx.appcompat.widget.AppCompatButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.*
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout
import org.android.go.sopt.R

object BindingExtensions {
    @JvmStatic
    @BindingAdapter("inputStyle")
    fun TextInputLayout.setInputStyle(i: Int?) {
        val green = context.getColor(R.color.mint_90)
        if (i == null || i <= ResultConstants.INVALID) // empty, invalid
            this.isErrorEnabled = true
        else { // valid
            this.isErrorEnabled = false
            val colorStateList = ColorStateList(
                arrayOf(intArrayOf(android.R.attr.state_focused), intArrayOf()),
                intArrayOf(green, green)
            )
            defaultHintTextColor =
                colorStateList // change hint text style for both states(collapsed & expanded)
            this.setBoxStrokeColorStateList(colorStateList)
        }
    }

    @JvmStatic
    @BindingAdapter("idGuideText")
    fun TextInputLayout.setIdText(i: Int?) {
        when (i) {
            null -> { // just focused
                this.hint = "아이디를 입력해주세요."
                this.error = null
            }

            ResultConstants.EMPTY -> { // empty
                this.hint = "유효한 아이디를 입력해주세요."
                this.error = "아이디가 비어있어요."
            }

            ResultConstants.INVALID -> { // invalid
                this.hint = "유효한 아이디를 입력해주세요."
                this.error = "조건: 영어, 숫자가 포함된 6~10자"
            }

            else -> { // valid
                this.hint = "올바른 아이디입니다."
                this.error = null
            }
        }
    }

    @JvmStatic
    @BindingAdapter("pwGuideText")
    fun TextInputLayout.setPwText(i: Int?) {
        when (i) {
            null -> { // just focused
                this.hint = "비밀번호를 입력해주세요."
                this.error = null
            }

            ResultConstants.EMPTY -> { // empty
                this.hint = "유효한 비밀번호를 입력해주세요."
                this.error = "비밀번호가 비어있어요."
            }

            ResultConstants.INVALID -> { // invalid
                this.hint = "유효한 비밀번호를 입력해주세요."
                this.error = "조건: 영문, 숫자, 특수문자가 포함된 6~12자"
            }

            else -> { // valid
                this.hint = "올바른 비밀번호입니다."
                this.error = null
            }
        }
    }

    @JvmStatic
    @BindingAdapter("nameGuideText")
    fun TextInputLayout.setNameText(i: Int?) {
        if (i == null) { // just focused
            this.hint = "이름을 입력해주세요."
            this.error = null
        } else {
            if (i >= ResultConstants.WRONG) { // valid
                this.hint = "이름이 입력되었습니다."
                this.error = null
            } else { // empty == invalid
                this.hint = "이름을 입력해주세요."
                this.error = "이름이 비어있어요."
            }
        }
    }

    @JvmStatic
    @BindingAdapter("skillGuideText")
    fun TextInputLayout.setSkillText(i: Int?) {
        if (i == null) { // just focused
            this.hint = "특기를 입력해주세요."
            this.error = null
        } else {
            if (i >= ResultConstants.WRONG) { // valid
                this.hint = "특기가 입력되었습니다."
                this.error = null
            } else { // empty == invalid
                this.hint = "특기를 입력해주세요."
                this.error = "특기가 비어있어요."
            }
        }
    }

    @JvmStatic
    @BindingAdapter("selectItem")
    fun ConstraintLayout.selectLayout(b: Boolean) {
        if (b) setBackgroundColor(getColor(context, R.color.pink_20))
        else setBackgroundColor(getColor(context, R.color.white))
    }

    @JvmStatic
    @BindingAdapter("btnText")
    fun AppCompatButton.setButtonText(b: Boolean) {
        text = if (b) "Delete" else "Edit"
    }
}