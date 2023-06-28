package org.android.go.sopt.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.android.go.sopt.util.ResultConstants

@Serializable
data class ResJoinDto(
    @SerialName("status")
    val status: Int = ResultConstants.WRONG,
    @SerialName("message")
    val message: String = "",
    @SerialName("data")
    val data: SignUpData = SignUpData()
) {
    @Serializable
    data class SignUpData(
        @SerialName("name")
        val name: String = "",
        @SerialName("skill")
        val skill: String = ""
    )
}