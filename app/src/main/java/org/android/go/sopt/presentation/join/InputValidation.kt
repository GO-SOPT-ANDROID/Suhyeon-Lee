package org.android.go.sopt.presentation.join

import android.util.Log
import android.util.Patterns
import java.util.regex.Pattern

class InputValidation(val isValid: Boolean, val errMsg: String)

fun String.validateId(): InputValidation {
    val regex = Regex("^[a-zA-Z0-9]{6,10}$")
    return if (trim().isEmpty()) {
        Log.e("ABC", "IS EMPTY")
        InputValidation(false, "ID is empty.")
    } else if (!this.matches(regex)) {
        Log.e("ABC", "INVALID")
        InputValidation(false, "영어, 숫자를 조합하여 6~10자로 만들어주세요.")
    } else {
        Log.e("ABC", "VALID")
        InputValidation(true, "")
    }
}

fun String.validatePw(): InputValidation {
    return if (trim().isEmpty()) {
        InputValidation(false, "PW is empty.")
    } else if (Pattern.compile("[A-Za-z0-9!@#$%^&]{8,12}").matcher(this).matches()) {
        InputValidation(false, "영어, 숫자, 특수문자를 조합하여 8~12자로 만들어주세요.")
    } else {
        InputValidation(true, "")
    }
}