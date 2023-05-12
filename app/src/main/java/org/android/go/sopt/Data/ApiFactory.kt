package org.android.go.sopt.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.android.go.sopt.BuildConfig
import retrofit2.Retrofit

object ApiFactory {
    private const val SOPT_URL = BuildConfig.SOPT_SERVER_URL
    private const val REQRES_URL = BuildConfig.REQRES_BASE_URL

    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()

    val retrofitSopt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_URL)
            .client(okhttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val retrofitReqres: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(REQRES_URL)
            .client(okhttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> createSopt(): T = retrofitSopt.create<T>(T::class.java)
    inline fun <reified T> createReqres(): T = retrofitReqres.create<T>(T::class.java)
}

object SrvcPool {
    val soptSrvc = ApiFactory.createSopt<SrvcInterface>()
    val reqresSrvc = ApiFactory.createReqres<SrvcInterface>()
}