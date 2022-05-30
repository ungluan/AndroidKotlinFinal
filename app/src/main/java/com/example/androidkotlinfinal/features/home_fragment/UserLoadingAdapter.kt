package com.example.androidkotlinfinal.features.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinfinal.databinding.ItemLoadingStateBinding

class UserLoadingAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<UserLoadingAdapter.UserLoadStateViewHolder>() {

    class UserLoadStateViewHolder(
        private val binding: ItemLoadingStateBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private val progressBar: ProgressBar = binding.progressBar
        private val tvErrorMessage: TextView = binding.tvErrorMessage
        private val btnRetry: Button = binding.btnRetry

        init {
            btnRetry.setOnClickListener {
                retry()
            }
        }

        fun bindState(loadState: LoadState){
            if(loadState is LoadState.Error){
                tvErrorMessage.text = loadState.error.localizedMessage
            }
            progressBar.isVisible = loadState is LoadState.Loading
            tvErrorMessage.isVisible = loadState !is LoadState.Loading
            btnRetry.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: UserLoadStateViewHolder, loadState: LoadState) {
        holder.bindState(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): UserLoadStateViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLoadingStateBinding.inflate(inflater)
        return UserLoadStateViewHolder(binding, retry)
    }
}