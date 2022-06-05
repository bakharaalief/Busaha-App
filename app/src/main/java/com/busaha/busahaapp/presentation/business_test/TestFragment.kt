package com.busaha.busahaapp.presentation.business_test

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.R
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.data.local.entity.AnswerEntity
import com.busaha.busahaapp.databinding.FragmentTestBinding
import com.busaha.busahaapp.domain.model.Answer
import com.busaha.busahaapp.domain.model.Question
import com.busaha.busahaapp.domain.model.QuestionOption
import com.busaha.busahaapp.domain.model.Questions
import com.busaha.busahaapp.presentation.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class TestFragment : Fragment(), View.OnClickListener {

    private lateinit var binding: FragmentTestBinding
    private lateinit var viewModel: TestViewModel
    private lateinit var listTest: List<Question>
    private lateinit var listTestOption: List<QuestionOption>
    private lateinit var answerData: AnswerEntity

    private var startTest = 0
    private var maxTest = 0
    private var currentTest = 0
    private var answerSaved = false

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

        setViewModel()

        //option button
        binding.testOptionABtn.setOnClickListener(this)
        binding.testOptionBBtn.setOnClickListener(this)
        binding.testOptionCBtn.setOnClickListener(this)
        binding.testOptionDBtn.setOnClickListener(this)
        binding.testOptionEBtn.setOnClickListener(this)

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
            R.id.test_option_e_btn -> optionBtnClicked('E')
            R.id.next_btn -> nextButton()
            R.id.prev_btn -> prevButton()
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(requireContext(), requireContext().dataStore)
        viewModel = ViewModelProvider(this, factory)[TestViewModel::class.java]

        viewModel.getTest().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    setAllTest(result.data)
                    updateTest()
                }
                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun setAllTest(questions: Questions) {
        maxTest = questions.count
        listTest = questions.listQuestion

        binding.testProgress.max = maxTest
    }

    private fun updateTest() {
        val number = currentTest + 1

        //set question
        binding.testProgress.progress = number
        binding.testNumberText.text = "$number".plus(" / $maxTest")
        binding.testQuizText.text = listTest[currentTest].question

        //make option button visible
        binding.testOptionABtn.visibility = View.GONE
        binding.testOptionBBtn.visibility = View.GONE
        binding.testOptionCBtn.visibility = View.GONE
        binding.testOptionDBtn.visibility = View.GONE
        binding.testOptionEBtn.visibility = View.GONE

        //setting prev button
        binding.prevBtn.visibility = if (currentTest == startTest) View.GONE else View.VISIBLE

        //set to normal
        answerSaved = false
        answerData = AnswerEntity(idQuestion = 0, idAnswer = 0, index = null)

        //set button color to default
        setColorButtonToDefault()

        //get option
        getOption()
    }

    private fun getOption() {
        viewModel.getTestOption(listTest[currentTest].id).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> showLoading(true)
                is Result.Success -> {
                    listTestOption = result.data.listOption
                    setOption()
                    isAnswerSaved()
                }
                is Result.Error -> {
                    showLoading(false)
                }
            }
        }
    }

    private fun setOption() {
        when (listTestOption.size) {
            2 -> {
                //set option
                binding.testOptionABtn.text = listTestOption[0].answer
                binding.testOptionBBtn.text = listTestOption[1].answer

                //make button visible
                binding.testOptionABtn.visibility = View.VISIBLE
                binding.testOptionBBtn.visibility = View.VISIBLE
            }
            3 -> {
                //set option
                binding.testOptionABtn.text = listTestOption[0].answer
                binding.testOptionBBtn.text = listTestOption[1].answer
                binding.testOptionCBtn.text = listTestOption[2].answer

                //make button visible
                binding.testOptionABtn.visibility = View.VISIBLE
                binding.testOptionBBtn.visibility = View.VISIBLE
                binding.testOptionCBtn.visibility = View.VISIBLE
            }
            4 -> {
                //set option
                binding.testOptionABtn.text = listTestOption[0].answer
                binding.testOptionBBtn.text = listTestOption[1].answer
                binding.testOptionCBtn.text = listTestOption[2].answer
                binding.testOptionDBtn.text = listTestOption[3].answer

                //make button visible
                binding.testOptionABtn.visibility = View.VISIBLE
                binding.testOptionBBtn.visibility = View.VISIBLE
                binding.testOptionCBtn.visibility = View.VISIBLE
                binding.testOptionDBtn.visibility = View.VISIBLE
            }
            5 -> {
                //set option
                binding.testOptionABtn.text = listTestOption[0].answer
                binding.testOptionBBtn.text = listTestOption[1].answer
                binding.testOptionCBtn.text = listTestOption[2].answer
                binding.testOptionDBtn.text = listTestOption[3].answer
                binding.testOptionEBtn.text = listTestOption[4].answer

                //make button visible
                binding.testOptionABtn.visibility = View.VISIBLE
                binding.testOptionBBtn.visibility = View.VISIBLE
                binding.testOptionCBtn.visibility = View.VISIBLE
                binding.testOptionDBtn.visibility = View.VISIBLE
                binding.testOptionEBtn.visibility = View.VISIBLE
            }
        }
    }

    private fun optionBtnClicked(option: Char) {
        val data = when (option) {
            'A' -> {
                Answer(
                    listTest[currentTest].id,
                    listTestOption[0].answerId,
                    listTestOption[0].index,
                )
            }
            'B' -> {
                Answer(
                    listTest[currentTest].id,
                    listTestOption[1].answerId,
                    listTestOption[1].index,
                )
            }
            'C' -> {
                Answer(
                    listTest[currentTest].id,
                    listTestOption[2].answerId,
                    listTestOption[2].index,
                )
            }
            'D' -> {
                Answer(
                    listTest[currentTest].id,
                    listTestOption[3].answerId,
                    listTestOption[3].index,
                )
            }
            'E' -> {
                Answer(
                    listTest[currentTest].id,
                    listTestOption[4].answerId,
                    listTestOption[4].index,
                )
            }
            else -> {
                Answer(
                    listTest[currentTest].id,
                    0,
                    null,
                )
            }
        }

        if (answerSaved) viewModel.updateAnswer(data) else viewModel.saveAnswer(data)
        toNextTest()
        binding.scrollview.fullScroll(ScrollView.FOCUS_UP)
    }

    private fun nextButton() {
        if (answerSaved) {
            toNextTest()
        } else {
            Toast.makeText(requireContext(), "Anda Belum Memilih Jawaban", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun prevButton() {
        toPrevTest()
    }

    private fun isAnswerSaved() {
        viewModel.isAnswerSaved(listTest[currentTest].id).observe(viewLifecycleOwner) {
            answerSaved = it
            if (answerSaved) {
                getAnswerSaved()
            } else {
                showLoading(false)
            }
        }
    }

    private fun getAnswerSaved() {
        viewModel.getAnswerSaved(listTest[currentTest].id).observe(viewLifecycleOwner) {
            if (it != null) {
                answerData = it
                if (listTest[currentTest].id == answerData.idQuestion) setColorForButton()
            }
        }
    }

    private fun setColorForButton() {
        when (listTestOption.size) {
            2 -> {
                when (answerData.idAnswer) {
                    listTestOption[0].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[1].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                }
            }
            3 -> {
                when (answerData.idAnswer) {
                    listTestOption[0].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[1].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[2].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                }
            }
            4 -> {
                when (answerData.idAnswer) {
                    listTestOption[0].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[1].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[2].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[3].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                }
            }
            5 -> {
                when (answerData.idAnswer) {
                    listTestOption[0].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[1].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[2].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[3].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.yellow))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
                    }
                    listTestOption[4].answerId -> {
                        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
                        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.yellow))
                    }
                }
            }
        }
        showLoading(false)
    }

    private fun setColorButtonToDefault() {
        binding.testOptionABtn.setTextColor(requireContext().getColor(R.color.white))
        binding.testOptionBBtn.setTextColor(requireContext().getColor(R.color.white))
        binding.testOptionCBtn.setTextColor(requireContext().getColor(R.color.white))
        binding.testOptionDBtn.setTextColor(requireContext().getColor(R.color.white))
        binding.testOptionEBtn.setTextColor(requireContext().getColor(R.color.white))
    }

    private fun toNextTest() {
        if (currentTest + 1 == maxTest) {
            toConfirmFrag()
        } else if (currentTest < maxTest) {
            currentTest++
            updateTest()
        }
    }

    private fun toPrevTest() {
        if (currentTest > startTest) {
            currentTest--
            updateTest()
        }
    }

    private fun toConfirmFrag() {
        val mFragmentManager = parentFragmentManager
        val mConfirmFragment = ConfirmFragment()

        mFragmentManager.beginTransaction().apply {
            replace(
                R.id.test_fragment_container,
                mConfirmFragment
            )
            commit()
        }
    }

    private fun showLoading(status: Boolean) {
        if (status) {
            binding.scrollview.visibility = View.GONE
            binding.loadingIndicator.visibility = View.VISIBLE
        } else {
            binding.scrollview.visibility = View.VISIBLE
            binding.loadingIndicator.visibility = View.GONE
        }
    }
}