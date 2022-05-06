package com.example.githubrepo.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Adapter
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubrepo.MainActivity
import com.example.githubrepo.R
import com.example.githubrepo.databinding.FragmentCurrentRepoBinding
import com.example.githubrepo.repository.GitHubRepository
import com.example.githubrepo.util.Constants
import com.example.githubrepo.util.Constants.Companion.readmeUrl
import com.example.githubrepo.util.Constants.Companion.webViewUrl
import com.example.githubrepo.util.GitHubResource
import com.example.githubrepo.viewModel.GitHubViewModel
import com.google.android.material.snackbar.Snackbar
import java.security.acl.Owner


class CurrentRepoFragment : Fragment() {

    private lateinit var currentRepoBinding: FragmentCurrentRepoBinding

    private lateinit var viewModel: GitHubViewModel

    private val args : CurrentRepoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        currentRepoBinding = FragmentCurrentRepoBinding.inflate(inflater, container, false)
        val view = currentRepoBinding.root

        viewModel = (activity as MainActivity).viewModel1

        currentRepoBinding.CurrentRepoName.text = args.repo.name
        Glide.with(view).load(args.repo.owner?.avatar_url).transition(DrawableTransitionOptions.withCrossFade()).into(currentRepoBinding.CurrentRepoImage)
        currentRepoBinding.CurrentRepoStarCount.text = args.repo.stargazers_count.toString()

        if(args.repo.description.toString().isNotEmpty()){
            currentRepoBinding.CurrentRepoDescription.text = args.repo.description.toString()
        }else{
            currentRepoBinding.CurrentRepoDescription.visibility = View.GONE
        }

        readmeUrl = args.repo.url + "/"
        Log.e("The url i made" , "$readmeUrl")

        viewModel.getReadme(readmeUrl.toString())

        viewModel.readme.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is GitHubResource.Success->{
                    hideProgressBar()
                    response.data?.let { resultResponse->
                        createWebView(resultResponse.html_url.toString())
                    }
                }
                is GitHubResource.Loading->{
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error Occurred $it", Toast.LENGTH_SHORT).show()
                    }
                }
                is GitHubResource.Error->{
                    showProgressBar()
                }
            }
        })

        currentRepoBinding.ReadmeWebView.webViewClient = WebViewClient()
        currentRepoBinding.ReadmeWebView.loadUrl(webViewUrl.toString())

        currentRepoBinding.GoBack.setOnClickListener {
            (activity as MainActivity).onBackPressed()
        }

        return view
    }

    private fun createWebView(url : String){
        currentRepoBinding.ReadmeWebView.webViewClient = WebViewClient()
        currentRepoBinding.ReadmeWebView.loadUrl(url)
    }

    private fun hideProgressBar(){
        currentRepoBinding.WebViewProgressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        currentRepoBinding.WebViewProgressBar.visibility = View.VISIBLE
    }

}