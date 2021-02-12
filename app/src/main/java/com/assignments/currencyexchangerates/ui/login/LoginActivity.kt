package com.assignments.currencyexchangerates.ui.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.assignments.currencyexchangerates.R
import com.assignments.currencyexchangerates.databinding.ActivityLoginBinding
import com.assignments.currencyexchangerates.ui.exchange_rate.ExchangeRateActivity
import com.google.android.material.snackbar.Snackbar

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickEvent()
        addTextChangeListener()
    }

    private fun clickEvent() {
        binding.loginBtn.setOnClickListener {
            val username = binding.emailEt.text.toString().trim()
            val password = binding.passwordEt.text.toString().trim()
            val isUsernameEmpty = TextUtils.isEmpty(username)
            val isPasswordEmpty = TextUtils.isEmpty(password)
            if (isUsernameEmpty || isPasswordEmpty) {
                if (isUsernameEmpty) {
                    binding.emailTil.isErrorEnabled = true
                    binding.emailTil.error = getString(R.string.email_validation)
                } else {
                    binding.emailTil.isErrorEnabled = false
                }
                if (isPasswordEmpty) {
                    binding.passwordTil.isErrorEnabled = true
                    binding.passwordTil.error = getString(R.string.password_validation)
                } else {
                    binding.passwordTil.isErrorEnabled = false
                }
            } else if (username == "test@android.com" && password == "123456") {
                val intent = Intent(this, ExchangeRateActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
                this.finish()
            } else {
                Snackbar.make(
                    binding.root,
                    getString(R.string.validate_login),
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun addTextChangeListener() {
        binding.emailEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val username = text.toString().trim()
                val isUsernameEmpty = TextUtils.isEmpty(username)

                if (isUsernameEmpty) {
                    if (isUsernameEmpty) {
                        binding.emailTil.isErrorEnabled = true
                        binding.emailTil.error = getString(R.string.email_validation)
                    } else {
                        binding.emailTil.isErrorEnabled = false
                    }
                } else {
                    binding.emailTil.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })

        binding.passwordEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val password = text.toString().trim()
                val isPasswordEmpty = TextUtils.isEmpty(password)

                if (isPasswordEmpty) {
                    if (isPasswordEmpty) {
                        binding.passwordTil.isErrorEnabled = true
                        binding.passwordTil.error = getString(R.string.password_validation)
                    } else {
                        binding.passwordTil.isErrorEnabled = false
                    }
                } else {
                    binding.passwordTil.isErrorEnabled = false
                }
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }
}