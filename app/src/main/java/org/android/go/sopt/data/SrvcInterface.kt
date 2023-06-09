package org.android.go.sopt.data

import okhttp3.MultipartBody
import org.android.go.sopt.data.model.ReqLogInDto
import org.android.go.sopt.data.model.ReqJoinDto
import org.android.go.sopt.data.model.ResLogInDto
import org.android.go.sopt.data.model.ResJoinDto
import org.android.go.sopt.data.model.ResUsersDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SrvcInterface {
    @POST("sign-up")
    fun join(
        @Body req: ReqJoinDto
    ): Call<ResJoinDto>

    @POST("sign-in")
    fun logIn(
        @Body req: ReqLogInDto
    ): Call<ResLogInDto>

    @GET("api/users?page=2")
    fun listUsers(): Call<ResUsersDto>

    @Multipart // "이 API의 ReqBody가 multi-part 형식으로 간단다"
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
    ): Call<Unit>
}