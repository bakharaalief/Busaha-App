package com.busaha.busahaapp.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.busaha.busahaapp.data.Result
import com.busaha.busahaapp.databinding.ActivityLoginBinding
import com.busaha.busahaapp.databinding.DialogLoadingBinding
import com.busaha.busahaapp.presentation.ViewModelFactory
import com.busaha.busahaapp.presentation.main.MainActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()
        setViewModel()

        binding.loginBtn.setOnClickListener {
            loginAction()
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
        val factory = ViewModelFactory.getInstance(dataStore)
        viewModel = ViewModelProvider(this, factory)[LoginViewModel::class.java]
    }

    private fun loginAction() {
        val emailEditText = binding.emailField
        val passwordEditText = binding.passField

        val email = emailEditText.editText?.text.toString()
        val password = passwordEditText.editText?.text.toString()

        when {
            email.isEmpty() -> emailEditText.error = "Email kosong"
            !checkEmailError(email) -> emailEditText.error = "Maaf ini bukan email"
            password.isEmpty() -> passwordEditText.error = "Pass kosong"
            password.length < 6 -> passwordEditText.error = "Pass kurang dari 6"
            else -> {
                //loading dialog
                val customBind = DialogLoadingBinding.inflate(layoutInflater)
                val loadingDialogBuilder = AlertDialog.Builder(this).apply {
                    setView(customBind.root)
                    setCancelable(false)
                }
                val loadingDialog = loadingDialogBuilder.create()

                viewModel.loginUser(email, password).observe(this) { result ->
                    when (result) {
                        is Result.Loading -> loadingDialog.show()
                        is Result.Success -> {
                            loadingDialog.dismiss()
                            viewModel.saveUser(result.data)
                            toMain()
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
            setTitle("Login Gagal")
            setMessage(message)
            create()
            show()
        }
    }

    private fun toMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}