package org.android.go.sopt.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class ReqLogInDto(
    @SerialName("id")
    val id: String = "",
    @SerialName("password")
    val password: String = ""
) : Parcelable
