package com.busaha.busahaapp.presentation.register

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.R
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.databinding.ActivityRegisterBinding
import com.busaha.busahaapp.databinding.DialogLoadingBinding
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.welcome.WelcomeActivity
import java.text.SimpleDateFormat
import java.util.*

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class RegisterActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var viewModel: RegisterViewModel
    private lateinit var date: Date
    private lateinit var calendar: Calendar

    private val genderItems = listOf("Laki - Laki", "Perempuan")
    private val statusItems = listOf("Mahasiswa", "Pekerja", "Wiraswasta")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setViewModel()
        setForm()

        binding.registerBtn.setOnClickListener {
            registerAction()
        }
    }

    private fun setActionBar() {
        setSupportActionBar(binding.materialToolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> true
        }
    }

    private fun setViewModel() {
        val factory = ViewModelFactory.getInstance(this, dataStore)
        viewModel = ViewModelProvider(this, factory)[RegisterViewModel::class.java]
    }

    private fun setForm() {
        //gender items

        val genderAdapter = ArrayAdapter(this, R.layout.item_list_dropdown, genderItems)
        (binding.genderField.editText as? AutoCompleteTextView)?.setAdapter(genderAdapter)

        //status items

        val statusAdapter = ArrayAdapter(this, R.layout.item_list_dropdown, statusItems)
        (binding.statusField.editText as? AutoCompleteTextView)?.setAdapter(statusAdapter)

        //date
        binding.ttlField.editText?.setOnClickListener {
            date = Date()
            calendar = Calendar.getInstance(TimeZone.getDefault())

            val datePickerDialog = DatePickerDialog(
                this,
                this::onDateSet,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }
    }

    override fun onDateSet(datePicker: DatePicker, year: Int, month: Int, day: Int) {
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, day)

        date = calendar.time
        binding.ttlField.editText?.text = toDateString(date)
    }

    private fun registerAction() {
        val emailEditText = binding.emailField
        val nameEditText = binding.nameField
        val genderEditText = binding.genderField
        val statusEditText = binding.statusField
        val dobEditText = binding.ttlField
        val passwordEditText = binding.passField
        val conPasswordEditText = binding.conPassField

        val email = emailEditText.editText?.text.toString()
        val name = nameEditText.editText?.text.toString()
        val gender = genderEditText.editText?.text.toString()
        val genderValue = if (gender == genderItems[0]) 'L' else 'P'
        val status = statusEditText.editText?.text.toString()
        val dob = dobEditText.editText?.text.toString()
        val password = passwordEditText.editText?.text.toString()
        val conPassword = conPasswordEditText.editText?.text.toString()

        when {
            email.isEmpty() -> emailEditText.error = resources.getString(R.string.empty_email)
            !checkEmailError(email) -> emailEditText.error = resources.getString(R.string.not_email)
            name.isEmpty() -> nameEditText.error = resources.getString(R.string.empty_name)
            gender.isEmpty() -> genderEditText.error = resources.getString(R.string.empty_gender)
            status.isEmpty() -> statusEditText.error = resources.getString(R.string.empty_status)
            dob.isEmpty() -> dobEditText.error = resources.getString(R.string.empty_birth)
            password.isEmpty() -> passwordEditText.error = resources.getString(R.string.empty_pass)
            password.length < 6 -> passwordEditText.error =
                resources.getString(R.string.pass_less_than_six)
            conPassword.isEmpty() -> conPasswordEditText.error =
                resources.getString(R.string.empty_confirm_pass)
            conPassword.length < 6 -> conPasswordEditText.error =
                resources.getString(R.string.confirm_pass_less_than_six)
            password != conPassword -> conPasswordEditText.error =
                resources.getString(R.string.confirm_pass_not_match)
            else -> {
                //loading dialog
                val customBind = DialogLoadingBinding.inflate(layoutInflater)
                val loadingDialogBuilder = AlertDialog.Builder(this).apply {
                    setView(customBind.root)
                    setCancelable(false)
                }
                val loadingDialog = loadingDialogBuilder.create()

                viewModel.registerUser(name, email, password, dob, genderValue, status)
                    .observe(this) { result ->
                        when (result) {
                            is Result.Loading -> loadingDialog.show()
                            is Result.Success -> {
                                loadingDialog.dismiss()
                                Toast.makeText(
                                    this,
                                    resources.getString(R.string.success_create_account),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                toLogin()
                            }
                            is Result.Error -> {
                                loadingDialog.dismiss()
                                errorAlert(result.error)
                            }
                        }
                    }
            }
        }
    }

    private fun checkEmailError(target: CharSequence): Boolean {
        return if (TextUtils.isEmpty(target)) false else android.util.Patterns.EMAIL_ADDRESS.matcher(
            target
        ).matches()
    }

    private fun errorAlert(message: String) {
        AlertDialog.Builder(this).apply {
            setTitle(resources.getString(R.string.failed_create_account))
            setMessage(message)
            create()
            show()
        }
    }

    @SuppressLint("SimpleDateFormat")
    private fun toDateString(date: Date): Editable {
        val d = SimpleDateFormat("dd-MM-yyyy")
        return Editable.Factory.getInstance().newEditable(d.format(date))
    }

    private fun toLogin() {
        val intent = Intent(this, WelcomeActivity::class.java)
        startActivity(intent)
        finish()
    }
}