package org.android.go.sopt.presentation.join

import android.util.Log
import android.util.Patterns
import java.util.regex.Pattern

class InputValidation(val isValid: Boolean, val errMsg: String)

fun String.validateId(): Int {
    val regex = Regex("^[a-zA-Z0-9]{6,10}$")
    return if (trim().isEmpty()) 0 // empty id
    else if (!this.matches(regex)) 2 // invalid
    else 1 // valid
}

fun String.validatePw(): Int {
    val regex = Regex("^[a-zA-Z0-9!@#$%^&*()]{6,12}$")
    return if (trim().isEmpty()) 0 // empty id
    else if (!this.matches(regex)) 2 // invalid
    else 1 // valid
}