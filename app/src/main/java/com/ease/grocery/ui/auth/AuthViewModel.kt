package com.ease.grocery.ui.auth

import com.ease.grocery.R
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class AuthState {
    object Idle : AuthState()
    object Loading : AuthState()
    data class OtpSent(val phone: String) : AuthState()
    object Verified : AuthState()
    data class Error(val message: Int) : AuthState()
}

class AuthViewModel : ViewModel() {

    private val _state = MutableLiveData<AuthState>(AuthState.Idle)
    val state: LiveData<AuthState> = _state

    private var generatedOtp: String = "1234"

    fun sendOtp(phone: String) {

        if (phone.length < 10) {
            _state.value = AuthState.Error(R.string.invalidPhone)
            return
        }

        _state.value = AuthState.Loading

        _state.value = AuthState.OtpSent(phone)
    }

    fun verifyOtp(inputOtp: String) {
        if (inputOtp == generatedOtp) {
            _state.value = AuthState.Verified
        } else {
            _state.value = AuthState.Error(R.string.wrongOtp)
        }
    }
}