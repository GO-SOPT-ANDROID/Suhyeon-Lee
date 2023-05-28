package org.android.go.sopt.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    // true -> show dialog, false -> close dialog
    private val _dialogFlag: MutableLiveData<Boolean> = MutableLiveData(false)
    val dialogFlag: LiveData<Boolean> = _dialogFlag

    fun setDialogFlag(b: Boolean) {
        _dialogFlag.value = b
    }
}