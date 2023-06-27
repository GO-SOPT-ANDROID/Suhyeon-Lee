package org.android.go.sopt.util

import android.content.SharedPreferences

object UserConstants {
    var id: String = "a"
    var name: String = "b"
    var skill: String = "c"

    var savedId: String? = null
    lateinit var sharedPreferences: SharedPreferences
    fun setAutoLogin(id: String?, pw: String?) {
        val spEditor = sharedPreferences.edit()
        spEditor.putString("id", id)
        spEditor.putString("pw", pw)
        spEditor.commit()
        savedId = id
    }
    fun getSharedPref(s: String): String? {
        return sharedPreferences.getString(s, null)
    }
}