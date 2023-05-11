package org.android.go.sopt.Data

import org.android.go.sopt.Data.Model.ReqSignUpDto
import org.android.go.sopt.Data.Model.ResSignUpDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignUpSrvc {
    @POST("sign-up")
    fun signUp(
        @Body req: ReqSignUpDto
    ): Call<ResSignUpDto>
}