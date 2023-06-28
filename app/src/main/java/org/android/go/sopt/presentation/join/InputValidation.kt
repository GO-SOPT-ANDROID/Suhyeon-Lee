package org.android.go.sopt.presentation.join

import org.android.go.sopt.util.InputResult

fun String.validateId(): Int {
    val regex = Regex("^[a-zA-Z0-9]{6,10}$")
    return if (trim().isEmpty()) InputResult.EMPTY // empty id 0
    else if (!this.matches(regex)) InputResult.INVALID // invalid 2
    else InputResult.WRONG // valid 1
}

fun String.validatePw(): Int {
    val regex = Regex("^[a-zA-Z0-9!@#$%^&*()]{6,12}$")
    return if (trim().isEmpty()) InputResult.EMPTY // empty id
    else if (!this.matches(regex)) InputResult.INVALID // invalid
    else InputResult.WRONG // valid
}

fun String.validateEmpty(): Int {
    return if (trim().isEmpty()) InputResult.EMPTY // empty
    else InputResult.RIGHT // valid
}