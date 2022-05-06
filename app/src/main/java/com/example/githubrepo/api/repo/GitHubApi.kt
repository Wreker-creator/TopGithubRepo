package com.example.githubrepo.api.repo

import com.example.githubrepo.model.repoModel.GitHubResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GitHubApi {

    @GET("repositories?q=+language:kotlin")suspend fun getTopRepositories(
        @Query("sort")
        sort : String,
        @Query("order")
        order : String
    ) : Response<GitHubResponse>

}