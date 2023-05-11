package org.android.go.sopt.Data.Model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReqLogInDto(
    @SerialName("id")
    val id: String,
    @SerialName("password")
    val password: String
)
