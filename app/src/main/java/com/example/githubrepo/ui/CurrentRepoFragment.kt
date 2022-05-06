package com.example.githubrepo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubrepo.R
import com.example.githubrepo.databinding.FragmentCurrentRepoBinding
import com.example.githubrepo.viewModel.GitHubViewModel


class CurrentRepoFragment : Fragment() {

    private lateinit var currentRepoBinding: FragmentCurrentRepoBinding

    private val args : CurrentRepoFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        currentRepoBinding = FragmentCurrentRepoBinding.inflate(inflater, container, false)
        val view = currentRepoBinding.root

        currentRepoBinding.CurrentRepoName.text = args.repo.name
        Glide.with(view).load(args.repo.owner?.avatar_url).transition(DrawableTransitionOptions.withCrossFade()).into(currentRepoBinding.CurrentRepoImage)
        currentRepoBinding.CurrentRepoStarCount.text = args.repo.stargazers_count.toString()

        if(args.repo.description.toString().isNotEmpty()){
            currentRepoBinding.CurrentRepoDescription.text = args.repo.description.toString()
        }else{
            currentRepoBinding.CurrentRepoDescription.visibility = View.GONE
        }


        return view
    }

}