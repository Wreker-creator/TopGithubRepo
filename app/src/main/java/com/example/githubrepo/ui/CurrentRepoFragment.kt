package com.example.githubrepo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubrepo.R
import com.example.githubrepo.databinding.FragmentCurrentRepoBinding


class CurrentRepoFragment : Fragment() {

    private lateinit var currentRepoBinding: FragmentCurrentRepoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        // Inflate the layout for this fragment
        currentRepoBinding = FragmentCurrentRepoBinding.inflate(inflater, container, false)
        val view = currentRepoBinding.root



        return view
    }

}