package com.busaha.busahaapp.presentation.business_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.FragmentTestBinding

class TestFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentTestBinding

    private var maxTest = 10
    private var currentTest = 1
    private var startTest = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.testProgress.max = maxTest
        binding.testProgress.progress = currentTest
        binding.testNumberText.text = "$currentTest".plus(" / $maxTest")

        binding.nextBtn.setOnClickListener(this)
        binding.prevBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.next_btn -> toNextTest()
            R.id.prev_btn -> toPrevTest()
        }
    }

    private fun toNextTest() {
        if (currentTest == maxTest) {
            toConfirmFrag()
        } else if (currentTest < maxTest) {
            currentTest++
            updateTest()
        }
    }

    private fun toPrevTest() {
        if (currentTest == startTest) {
            Toast.makeText(context, "Kamu di awal soal :)", Toast.LENGTH_SHORT).show()
        } else if (currentTest > startTest) {
            currentTest--
            updateTest()
        }
    }

    private fun updateTest() {
        binding.testNumberText.text = "$currentTest".plus(" / $maxTest")
        binding.testProgress.progress = currentTest
    }

    private fun toConfirmFrag() {
        val mConfirmFragment = ConfirmFragment()
        val mFragmentManager = parentFragmentManager

        val mBundle = Bundle()
        mBundle.putInt(MAX_TEST, 10)
        mBundle.putInt(CURRENT_TEST, currentTest)

        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.test_fragment_container,
                mConfirmFragment
            )
            addToBackStack(null)
            commit()
        }
    }

    companion object {
        const val MAX_TEST = "MAX_TEST"
        const val CURRENT_TEST = "CURRENT_TEST"
    }
}