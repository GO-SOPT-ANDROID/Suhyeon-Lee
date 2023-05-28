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

    /********* 김지피티 버전 *********/
    val httpLoggingInterceptor = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)

    private val okhttpClient = OkHttpClient.Builder()
        .addInterceptor(httpLoggingInterceptor)
        .build()
    /**************************/

    private val client by lazy {
        OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }).build()
    }

    /* 김지피티 버전
    val retrofitSopt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_URL)
            .client(okhttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }
    */

    val retrofitSopt: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_URL)
            .client(okhttpClient)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client).build()
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