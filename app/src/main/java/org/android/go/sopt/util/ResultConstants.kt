package org.android.go.sopt.util

object ResultConstants {
    const val EMPTY: Int = -2 // invalid, even empty
    const val INVALID: Int = -1 // invalid
    const val WRONG: Int = 0 // just valid, wrong or unknown (if right) yet
    const val RIGHT: Int = 1 // valid and right
}