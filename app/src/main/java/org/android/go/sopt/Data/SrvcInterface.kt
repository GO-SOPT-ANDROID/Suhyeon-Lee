package org.android.go.sopt.Data

import okhttp3.MultipartBody
import org.android.go.sopt.Data.Model.ReqLogInDto
import org.android.go.sopt.Data.Model.ReqSignUpDto
import org.android.go.sopt.Data.Model.ResLogInDto
import org.android.go.sopt.Data.Model.ResSignUpDto
import org.android.go.sopt.Data.Model.ResUsersDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SrvcInterface {
    @POST("sign-up")
    fun signUp(
        @Body req: ReqSignUpDto
    ): Call<ResSignUpDto>

    @POST("sign-in")
    fun logIn(
        @Body req: ReqLogInDto
    ): Call<ResLogInDto>

    @GET("api/users?page=2")
    fun listUsers(): Call<ResUsersDto>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Call<Unit>
}