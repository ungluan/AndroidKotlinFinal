package com.example.androidkotlinfinal.features.user_detail_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.androidkotlinfinal.databinding.FragmentUserDetailBinding

class UserDetailFragment : Fragment() {
    private lateinit var binding: FragmentUserDetailBinding
    private val viewModel by lazy {
        val application = requireActivity().application
        val user = UserDetailFragmentArgs.fromBundle(requireArguments()).user
        val viewModelFactory = UserDetailViewModelFactory(user,application)
        ViewModelProvider(this,viewModelFactory)[UserDetailViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater,container,false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }


}