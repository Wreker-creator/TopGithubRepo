package com.example.githubrepo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.githubrepo.R
import com.example.githubrepo.databinding.FragmentTopRepoBinding


class TopRepoFragment : Fragment() {

    private lateinit var binding: FragmentTopRepoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopRepoBinding.inflate(inflater, container, false)
        val view = binding.root



        return view
    }


}