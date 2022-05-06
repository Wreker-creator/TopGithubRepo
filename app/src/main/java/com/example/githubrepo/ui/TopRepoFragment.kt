package com.example.githubrepo.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubrepo.MainActivity
import com.example.githubrepo.R
import com.example.githubrepo.adapter.TopRepoAdapter
import com.example.githubrepo.databinding.FragmentTopRepoBinding
import com.example.githubrepo.util.GitHubResource
import com.example.githubrepo.viewModel.GitHubViewModel


class TopRepoFragment : Fragment() {

    private lateinit var binding: FragmentTopRepoBinding

    private lateinit var viewModel : GitHubViewModel

    private lateinit var adapter: TopRepoAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopRepoBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = (activity as MainActivity).viewModel1

        adapter = TopRepoAdapter()
        binding.Recycler1.layoutManager = LinearLayoutManager(activity)
        binding.Recycler1.adapter = adapter

        viewModel.topRepositories.observe(viewLifecycleOwner) { response ->
            when (response) {
                is GitHubResource.Success -> {
                    hideProgressBar()
                    response.data?.let { repoResponse ->
                        adapter.diffUtil.submitList(repoResponse.items.toList())
                    }
                }
                is GitHubResource.Error -> {
                    hideProgressBar()
                    response.message?.let {
                        Toast.makeText(activity, "An error Occurred $it", Toast.LENGTH_SHORT).show()
                    }
                }

                is GitHubResource.Loading -> {
                    showProgressBar()
                }
            }
        }

        adapter.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("repo", it)
            }
            findNavController().navigate(R.id.action_topRepoFragment_to_currentRepoFragment, bundle)
        }

        return view
    }

    private fun hideProgressBar(){
        binding.ProgressBar.visibility = View.GONE
    }

    private fun showProgressBar(){
        binding.ProgressBar.visibility = View.VISIBLE
    }

}