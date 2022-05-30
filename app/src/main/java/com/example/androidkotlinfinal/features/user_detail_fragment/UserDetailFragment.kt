package com.example.androidkotlinfinal.features.user_detail_fragment

import android.content.Intent
import android.net.Uri
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
        val viewModelFactory = UserDetailViewModelFactory(user, application)
        ViewModelProvider(this, viewModelFactory)[UserDetailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.isSuccess.observe(viewLifecycleOwner) { success ->
            if (success) {
                binding.overlayWhite.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }
        }

        binding.txtGithub.setOnClickListener {
            browerUrl(viewModel.user.value?.htmlUrl)
        }
        binding.txtBlog.setOnClickListener {
            browerUrl(viewModel.user.value?.blog)
        }
    }

    private fun browerUrl(url: String?) {
        url?.let {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }
    }
}