package org.android.go.sopt.util

import android.util.Log
import android.widget.Button
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object BindingExtensions {
    @JvmStatic
    @BindingAdapter("errorText")
    fun TextInputLayout.setErrorMsg(errorText: String?) {
        if (errorText.isNullOrEmpty()) this.isErrorEnabled = false
        else {
            this.isErrorEnabled = true
            this.error = errorText
        }
    }
}