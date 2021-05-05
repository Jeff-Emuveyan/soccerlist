package com.seamfix.maontestapplication.data.source.remote
import android.content.Context
import com.seamfix.maontestapplication.MainApplication
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit


object ApiClient {

    fun getClient(appContext: Context): Retrofit{

        val httpClient =  OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .callTimeout(60, TimeUnit.SECONDS)
            .retryOnConnectionFailure(false)
        httpClient.addInterceptor { chain ->
            val original = chain.request();

            val request = original.newBuilder()
                .header("X-Auth-Token", "0bd225e371fa47d89ff039f855a09311")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Accept", "*/*")
                .header("Connection", "keep-alive")
                .build()

            chain.proceed(request);
        }

        val  client = httpClient.build()
        val application = appContext.applicationContext as MainApplication

        return Retrofit.Builder()
            .baseUrl(application.baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
}