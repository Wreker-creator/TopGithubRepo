package com.example.githubrepo.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepo.repository.GitHubRepository

class GitHubViewModelFactory(
    val app : Application,
    val repository: GitHubRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GitHubViewModel(app, repository) as T
    }

}