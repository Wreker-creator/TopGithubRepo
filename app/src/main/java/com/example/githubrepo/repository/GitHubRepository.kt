package com.example.githubrepo.repository

import com.example.githubrepo.api.RetrofitInstance

class GitHubRepository {

    suspend fun getTopRepositories(sort : String, order : String) = RetrofitInstance.apiCall.getTopRepositories(sort, order)

}