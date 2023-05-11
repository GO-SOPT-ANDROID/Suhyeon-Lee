package org.android.go.sopt.Data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.android.go.sopt.BuildConfig
import retrofit2.Retrofit

object ApiFactorySopt {
    private const val SOPT_URL = BuildConfig.SOPT_SERVER_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(SOPT_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object ApiFactoryReqres {
    private const val REQRES_URL = BuildConfig.REQRES_BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(REQRES_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create<T>(T::class.java)
}

object SrvcPool {
    val soptSrvc = ApiFactorySopt.create<SrvcInterface>()
    val reqresSrvc = ApiFactoryReqres.create<SrvcInterface>()
}