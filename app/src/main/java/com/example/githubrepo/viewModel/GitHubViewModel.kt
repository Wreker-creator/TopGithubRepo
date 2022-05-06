package com.example.githubrepo.viewModel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.githubrepo.MyApplication
import com.example.githubrepo.model.GitHubResponse
import com.example.githubrepo.repository.GitHubRepository
import com.example.githubrepo.util.Constants
import com.example.githubrepo.util.GitHubResource
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException

class GitHubViewModel(
    app : Application,
    val repository: GitHubRepository
) : AndroidViewModel(app){

    val topRepositories : MutableLiveData<GitHubResource<GitHubResponse>> = MutableLiveData()
    var topRepositoriesResponse : GitHubResponse? = null

    init {
        getTopRepositories(sort = Constants.sort, order = Constants.sort)
    }

    fun getTopRepositories(sort : String, order : String) = viewModelScope.launch {
        safeTopRepositoriesCall(sort, order)
    }

    private fun handleTopRepositoriesCall(response : Response<GitHubResponse>) : GitHubResource<GitHubResponse>{

        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                if(topRepositoriesResponse == null){
                    topRepositoriesResponse = resultResponse
                }else{
                    val oldResponse = topRepositoriesResponse?.items
                    val newResponse = resultResponse.items
                    oldResponse?.addAll(newResponse)
                }
                return GitHubResource.Success(topRepositoriesResponse?:resultResponse)
            }
        }
        return GitHubResource.Error(response.message())
    }

    private suspend fun safeTopRepositoriesCall(sort: String, order: String){
        topRepositories.postValue(GitHubResource.Loading())
        try {
            if(hasInternetFunction()){
                val response = repository.getTopRepositories(sort, order)
                topRepositories.postValue(handleTopRepositoriesCall(response))
            }else{
                topRepositories.postValue(GitHubResource.Error("No Internet Connection"))
            }
        }catch (t:Throwable){
            when(t){
                is IOException -> topRepositories.postValue(GitHubResource.Error("Network Failure"))
                else -> topRepositories.postValue(GitHubResource.Error("Conversion Error"))
            }
        }
    }

    private fun hasInternetFunction() : Boolean{
        val connectivityManager = getApplication<MyApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when{
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.activeNetworkInfo?.run {
                return when(type){
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }

}