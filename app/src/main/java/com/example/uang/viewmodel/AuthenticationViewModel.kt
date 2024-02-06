package com.example.uang.viewmodel
import androidx.lifecycle.ViewModel
import com.example.uang.SignInResultData
import com.example.uang.SignState
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


class AuthenticationViewMode : ViewModel() {

    private val auth = Firebase.auth

    private val _state = MutableStateFlow(SignState())
    val state = _state.asStateFlow()

    fun onSignResult(result: SignInResultData) {
        _state.update {
            it.copy(
                isSignInSuccessfull = result.data != null,
                signInError = result.errorMessage
            )
        }
    }

    fun resetState() {
        _state.update { SignState() }
    }
}