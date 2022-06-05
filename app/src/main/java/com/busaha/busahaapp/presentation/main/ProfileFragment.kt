package com.busaha.busahaapp.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.databinding.FragmentProfileBinding
import com.busaha.busahaapp.domain.model.UserDetail
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.welcome.WelcomeActivity
import java.text.SimpleDateFormat

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

        viewModel.getUserData().observe(viewLifecycleOwner) {
            id = it.localId
            getUserDetail()
        }
    }

    private fun toWelcome() {
        viewModel.signOut()
        val intent = Intent(context, WelcomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        requireActivity().finish()
    }


    private fun getUserDetail() {
        viewModel.getDetailUser(id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    userDetail = result.data
                    isiData()
                    showLoading(false)
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }
        }
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            binding.loadingIndicator.visibility = View.VISIBLE
            binding.profileInfo.visibility = View.GONE
        } else {
            binding.loadingIndicator.visibility = View.GONE
        }
    }

    private fun isiData() {
        binding.tvUsername.text = userDetail.name
        binding.tvUserEmail.text = userDetail.email
        binding.tvUserStatus.text = userDetail.status
        binding.tvDobUser.text = stringToDate(userDetail.dob)
        binding.tvGenderUser.text = if (userDetail.gender == 'L') "Laki - Laki" else "Perempuan"

        binding.profileInfo.visibility = View.VISIBLE
    }

    @SuppressLint("SimpleDateFormat")
    private fun stringToDate(date: String): String {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val outputFormatter = SimpleDateFormat("dd-MM-yyyy")
        val d = inputFormatter.parse(date)
        return outputFormatter.format(d!!)
    }
}