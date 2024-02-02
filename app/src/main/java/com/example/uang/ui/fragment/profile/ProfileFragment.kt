package com.example.uang.ui.fragment.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.uang.R
import com.example.uang.UserData
import com.example.uang.databinding.FragmentIncomeBinding
import com.example.uang.databinding.FragmentProfileBinding
import com.example.uang.util.GoogleAuthHelper
import com.google.android.gms.auth.api.identity.Identity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var userData: UserData
    private lateinit var googleHelper: GoogleAuthHelper


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        googleHelper =
            GoogleAuthHelper(Identity.getSignInClient(requireContext()), requireContext())

        userData = googleHelper.getSignedUser()!!

        Glide.with(requireContext()).load(userData.profilePrictureUser)
            .into(binding.profilePic)
        binding.profileName.text = userData.userName



        return binding.root
    }








    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}