package com.busaha.busahaapp.presentation.business_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.FragmentConfirmBinding

class ConfirmFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentConfirmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.yesBtn.setOnClickListener(this)
        binding.checkBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.yes_btn -> Toast.makeText(context, "Yes", Toast.LENGTH_SHORT).show()
            R.id.check_btn -> Toast.makeText(context, "Check", Toast.LENGTH_SHORT).show()
        }
    }
}