package com.example.githubrepo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.githubrepo.repository.GitHubRepository
import com.example.githubrepo.viewModel.GitHubViewModel
import com.example.githubrepo.viewModel.GitHubViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var viewModel1 : GitHubViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = GitHubRepository()
        val viewModelProviderFactory = GitHubViewModelFactory(application, repository)
        viewModel1 = ViewModelProvider(this, viewModelProviderFactory).get(GitHubViewModel::class.java)

    }
}