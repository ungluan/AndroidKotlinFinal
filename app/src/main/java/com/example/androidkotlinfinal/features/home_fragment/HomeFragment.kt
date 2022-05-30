package com.example.androidkotlinfinal.features.home_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.map
import com.example.androidkotlinfinal.database.entities.asDomainModel
import com.example.androidkotlinfinal.databinding.FragmentHomeBinding
import com.example.androidkotlinfinal.domain.User
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/***
- Tránh dùng constraint layout vs item của recycler view
- Bởi vì: ConstraintLayout sẽ phải tính toán lại rất nhiều dựa trên các Constraint -> Ngốn CPU
 ***/
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater)
        viewModel = ViewModelProvider(requireActivity())[HomeViewModel::class.java]

        binding.viewModel = this.viewModel
        binding.lifecycleOwner = this
        val pagingAdapter = UserListAdapter(OnClickListener { user ->
            navigateToUserDetailFragment(user)
        })
        binding.recyclerView.adapter = pagingAdapter.withLoadStateFooter(
            footer =  UserLoadingAdapter { pagingAdapter.retry() }
        )

        lifecycleScope.launch {
            viewModel.fetchUsers().collectLatest { pagingData ->
                pagingAdapter.submitData(pagingData.map { it.asDomainModel() } )
            }
        }
        // Why setHasFixedSize = true then my RecyclerView is Empty?
//            recyclerView.setHasFixedSize(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.swipeRefresh.setOnRefreshListener {
//            viewModel.refresherUser()
//        }

//        viewModel.isCompletedRefresh.observe(viewLifecycleOwner) { completed ->
//            if (completed) binding.swipeRefresh.isRefreshing = false
//        }
    }

    private fun navigateToUserDetailFragment(user: User) {
        val action = HomeFragmentDirections.actionHomeFragmentToUserDetailFragment(user)
        findNavController().navigate(action)
    }
}