package com.example.githubrepo.api.readme

import com.example.githubrepo.api.repo.GitHubApi
import com.example.githubrepo.util.Constants
import com.example.githubrepo.util.Constants.Companion.readmeUrl
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReadmeRetrofitInstance(url : String) {

    val loggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder().addInterceptor(loggingInterceptor).build()

    private fun getRetrofit(url : String) : Retrofit{
        return Retrofit.Builder().baseUrl(url).client(client).addConverterFactory(GsonConverterFactory.create()).build()
    }

    val retrofit = getRetrofit(url)
    val readmeApiCall = retrofit.create(ReadmeApi::class.java)


}