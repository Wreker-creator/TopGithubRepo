package com.example.githubrepo.repository

import com.example.githubrepo.api.readme.ReadmeRetrofitInstance
import com.example.githubrepo.api.repo.RepositoryRetrofitInstance
import com.example.githubrepo.util.Constants.Companion.readmeUrl

class GitHubRepository {

    suspend fun getTopRepositories(sort : String, order : String) = RepositoryRetrofitInstance.apiCall.getTopRepositories(sort, order)
    suspend fun getReadme(url : String) = ReadmeRetrofitInstance(url).readmeApiCall.getReadme()

}