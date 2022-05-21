package com.example.androidkotlinfinal.features.home_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.androidkotlinfinal.databinding.FragmentHomeBinding


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

        binding.apply {
            viewModel = this@HomeFragment.viewModel
            lifecycleOwner = this@HomeFragment
            recyclerView.adapter = UserListAdapter(OnClickListener {
                Toast.makeText(context,it.toString(),Toast.LENGTH_LONG).show()
            })
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}