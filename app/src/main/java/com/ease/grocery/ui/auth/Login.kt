package com.ease.grocery.ui.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.ViewModelProvider
import com.ease.grocery.R
import com.ease.grocery.ui.home.HomeActivity

class Login : AppCompatActivity() {

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContentView(R.layout.activity_login)

        val phone = findViewById<EditText>(R.id.etPhone)
        val otp = findViewById<EditText>(R.id.etOtp)
        val verifyBtn = findViewById<Button>(R.id.btnVerify)

        viewModel = ViewModelProvider(this)[AuthViewModel::class.java]

         phone.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                viewModel.sendOtp(phone.text.toString())
            }
        }


        verifyBtn.setOnClickListener {
            viewModel.verifyOtp(otp.text.toString())
        }


        viewModel.state.observe(this) { state ->
            when (state) {

                is AuthState.Loading -> {
                    Toast.makeText(this, "Sending OTP...", Toast.LENGTH_SHORT).show()
                }

                is AuthState.OtpSent -> {
                    Toast.makeText(this, "OTP sent to ${state.phone}", Toast.LENGTH_SHORT).show()
                }

                is AuthState.Verified -> {
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }

                is AuthState.Error -> {
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }
}