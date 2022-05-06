package com.example.githubrepo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.githubrepo.databinding.RecyclerItemBinding
import com.example.githubrepo.model.repoModel.Item

class TopRepoAdapter : RecyclerView.Adapter<TopRepoAdapter.MyViewHolder>() {

    inner class MyViewHolder(val adapterBinding: RecyclerItemBinding) : RecyclerView.ViewHolder(adapterBinding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Item>(){
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem.html_url == newItem.html_url
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    }
    val diffUtil = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentObject = diffUtil.currentList[position]

        holder.adapterBinding.RepoName.text = currentObject.name

        val url = currentObject.owner?.avatar_url

        Glide.with(holder.itemView)
            .load(url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(holder.adapterBinding.RepoImage)

        holder.itemView.setOnClickListener {
            onItemClickListener?.let { it(currentObject) }
        }


    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    private var onItemClickListener : ((Item) -> Unit)? = null

    fun setOnItemClickListener(listener: (Item) -> Unit){
        onItemClickListener = listener
    }


}