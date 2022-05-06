package com.example.githubrepo.repository

import com.example.githubrepo.api.RetrofitInstance
import com.example.githubrepo.util.Constants.Companion.order
import com.example.githubrepo.util.Constants.Companion.sort

class GitHubRepository {

    suspend fun getTopRepositories() = RetrofitInstance.apiCall.getTopRepositories(sort, order)

}