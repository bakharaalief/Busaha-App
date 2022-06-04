package com.busaha.busahaapp.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.data.remote.response.UserItem
import com.busaha.busahaapp.databinding.FragmentProfileBinding
import com.busaha.busahaapp.domain.entity.UserDetail
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.welcome.WelcomeActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewModel: ProfileViewModel
    private lateinit var userDetail: UserDetail
    private var id = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setViewModel()

        binding.logoutBtn.setOnClickListener {
            toWelcome()
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext(), requireContext().dataStore)
        viewModel = ViewModelProvider(viewModelStore, factory)[ProfileViewModel::class.java]

        viewModel.getUserData().observe(viewLifecycleOwner){
            id = it.localId
            getUserDetail()
        }
    }

    private fun toWelcome() {
        viewModel.signOut()
        val intent = Intent(context, WelcomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }



    private fun getUserDetail() {
        viewModel.getDetailUser(id).observe(viewLifecycleOwner){result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    userDetail = result.data
                    isiData(userDetail)
                    showLoading(false)
                }
                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(status: Boolean){
        if (status) {
            binding.loadingIndicator.visibility = View.VISIBLE
        } else {
            binding.loadingIndicator.visibility = View.GONE
        }
    }

    private fun isiData(data: UserDetail){
        binding.apply {
            tvUsername.text = data.name
            tvUserEmail.text = data.email
            tvUserStatus.text = data.status
            tvDobUser.text = data.dob
            tvGenderUser.text = data.gender.toString()
        }
    }

}