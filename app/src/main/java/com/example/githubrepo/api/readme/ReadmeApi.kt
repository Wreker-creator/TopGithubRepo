package com.example.githubrepo.api.readme

import com.example.githubrepo.model.readmeModel.ReadmeResponse
import retrofit2.Response
import retrofit2.http.GET

interface ReadmeApi {

    @GET("readme")
    suspend fun getReadme() : Response<ReadmeResponse>

}