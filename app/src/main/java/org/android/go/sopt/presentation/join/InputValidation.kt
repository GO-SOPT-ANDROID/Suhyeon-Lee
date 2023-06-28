package org.android.go.sopt.presentation.join

import org.android.go.sopt.util.ResultConstants

fun String.validateId(): Int {
    val regex = Regex("^[a-zA-Z0-9]{6,10}$")
    return if (trim().isEmpty()) ResultConstants.EMPTY // empty id 0
    else if (!this.matches(regex)) ResultConstants.INVALID // invalid 2
    else ResultConstants.WRONG // valid 1
}

fun String.validatePw(): Int {
    val regex = Regex("^[a-zA-Z0-9!@#$%^&*()]{6,12}$")
    return if (trim().isEmpty()) ResultConstants.EMPTY // empty id
    else if (!this.matches(regex)) ResultConstants.INVALID // invalid
    else ResultConstants.WRONG // valid
}

fun String.validateEmpty(): Int {
    return if (trim().isEmpty()) ResultConstants.EMPTY // empty
    else ResultConstants.RIGHT // valid
}