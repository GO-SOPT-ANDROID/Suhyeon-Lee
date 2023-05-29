package org.android.go.sopt.presentation.join

import android.util.Log
import android.util.Patterns
import java.util.regex.Pattern

class InputValidation(val isValid: Boolean, val errMsg: String)

fun String.validateId(): InputValidation {
    val regex = Regex("^[a-zA-Z0-9]{6,10}$")
    return if (trim().isEmpty()) {
        InputValidation(false, "아이디가 비어있어요.")
    } else if (!this.matches(regex)) {
        InputValidation(false, "조건: 영어, 숫자가 포함된 6~10자")
    } else {
        InputValidation(true, "")
    }
}

fun String.validatePw(): InputValidation {
    val regex = Regex("^[a-zA-Z0-9!@#$%^&*()]{6,12}$")
    return if (trim().isEmpty()) {
        InputValidation(false, "비밀번호가 비어있어요.")
    } else if (!this.matches(regex)) {
        InputValidation(false, "조건: 영어, 숫자, 특수문자가 포함된 6~12자")
    } else {
        InputValidation(true, "")
    }
}