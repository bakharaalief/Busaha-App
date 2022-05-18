package com.busaha.busahaapp.presentation.business_test

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.busaha.busahaapp.R
import com.busaha.busahaapp.databinding.FragmentTestBinding

class TestFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentTestBinding

    private var startTest = 1
    private var maxTest = 0
    private var currentTest = 1

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

        maxTest = arguments?.getInt(MAX_TEST, 1) ?: 1

        //quiz progress, number, and text
        binding.testProgress.max = maxTest
        binding.testProgress.progress = currentTest
        binding.testNumberText.text = "$currentTest".plus(" / $maxTest")

        //visible prev button
        binding.prevBtn.visibility = if (currentTest == startTest) View.GONE else View.VISIBLE

        //option button
        binding.testOptionABtn.setOnClickListener(this)
        binding.testOptionBBtn.setOnClickListener(this)
        binding.testOptionCBtn.setOnClickListener(this)
        binding.testOptionDBtn.setOnClickListener(this)

        //prev and next button
        binding.nextBtn.setOnClickListener(this)
        binding.prevBtn.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.test_option_a_btn -> optionBtnClicked('A')
            R.id.test_option_b_btn -> optionBtnClicked('B')
            R.id.test_option_c_btn -> optionBtnClicked('C')
            R.id.test_option_d_btn -> optionBtnClicked('D')
            R.id.next_btn -> toNextTest()
            R.id.prev_btn -> toPrevTest()
        }
    }

    private fun optionBtnClicked(option: Char) {
        Toast.makeText(context, "Mencet Tombol $option", Toast.LENGTH_SHORT).show()
        toNextTest()
        binding.scrollview.fullScroll(ScrollView.FOCUS_UP)
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
        binding.prevBtn.visibility = if (currentTest == startTest) View.GONE else View.VISIBLE
    }

    private fun toConfirmFrag() {
        val mFragmentManager = parentFragmentManager
        val mConfirmFragment = ConfirmFragment()

        val mBundle = Bundle()
        mBundle.putInt(MAX_TEST, maxTest)
        mConfirmFragment.arguments = mBundle

        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.test_fragment_container,
                mConfirmFragment
            )
            commit()
        }
    }

    companion object {
        const val MAX_TEST = "max_test"
    }
}