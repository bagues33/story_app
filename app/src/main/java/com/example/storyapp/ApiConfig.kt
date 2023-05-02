package com.example.storyapp

import android.content.Context
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {

    companion object{

        private fun getInterceptor(token: String?): OkHttpClient {
            val loggingInterceptor =
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        class AuthInterceptor(private var token: String): Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()

                request = if (request.header("No-Authentication") == null && token.isNotEmpty()) {
                    val finalToken = "Bearer $token"
                    request.newBuilder()
                        .addHeader("Authorization", finalToken)
                        .build()
                } else {
                    request.newBuilder()
                        .build()
                }

                return chain.proceed(request)
            }
        }

        fun getApiService(context: Context): ApiService {

            val sessionManager = SessionManager(context)
            val token = sessionManager.getToken

            val retrofit = Retrofit.Builder()
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}


