package com.busaha.busahaapp.presentation.business_test

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.FragmentConfirmBinding
import com.busaha.busahaapp.presentation.business_result.BusinessResultActivity

class ConfirmFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentConfirmBinding

    private var maxTest = 0

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

        maxTest = arguments?.getInt(TestFragment.MAX_TEST, 0) ?: 0

        binding.yesBtn.setOnClickListener(this)
        binding.checkBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.yes_btn -> toResult()
            R.id.check_btn -> toTestFrag()
        }
    }

    private fun toResult() {
        val intent = Intent(context, BusinessResultActivity::class.java)
        startActivity(intent)
        activity?.finish()
    }

    private fun toTestFrag() {
        val mFragmentManager = parentFragmentManager
        val mTestFragment = TestFragment()

        val mBundle = Bundle()
        mBundle.putInt(TestFragment.MAX_TEST, maxTest)
        mTestFragment.arguments = mBundle

        mFragmentManager
            .beginTransaction()
            .replace(
                R.id.test_fragment_container,
                mTestFragment,
            )
            .commit()
    }
}