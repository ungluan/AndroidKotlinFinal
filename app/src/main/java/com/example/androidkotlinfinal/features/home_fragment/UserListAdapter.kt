package com.example.androidkotlinfinal.features.home_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinfinal.databinding.UserItemBinding
import com.example.androidkotlinfinal.domain.User
import timber.log.Timber


class UserListAdapter(private val onClickListener: OnClickListener) :
    PagingDataAdapter<User, UserListAdapter.ViewHolder>(DiffCallback()) {
    class ViewHolder(val binding: UserItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = getItem(position)

        user?.let {
            holder.bind(user)
            holder.binding.root.setOnClickListener {
                onClickListener.onClick(user)
            }
        }
    }
}

class DiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }
}

class OnClickListener(private val clickListener: (User: User) -> Unit) {
    fun onClick(user: User) = clickListener(user)
}