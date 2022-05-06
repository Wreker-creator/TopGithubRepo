package com.example.githubrepo.model

data class GitHubModel(
    val incomplete_results: Boolean,
    val items: List<Item>,
    val total_count: Int
)