package com.jeywoods.reciperealm.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class AuthUiState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

class AuthViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState: StateFlow<AuthUiState> = _uiState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            runCatching {
                auth.signInWithEmailAndPassword(email, password).await()
            }.onSuccess {
                _uiState.value = AuthUiState(isSuccess = true)
            }.onFailure { e ->
                _uiState.value = AuthUiState(error = friendlyError(e.message))
            }
        }
    }

    fun signUp(email: String, password: String) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            runCatching {
                auth.createUserWithEmailAndPassword(email, password).await()
            }.onSuccess {
                _uiState.value = AuthUiState(isSuccess = true)
            }.onFailure { e ->
                _uiState.value = AuthUiState(error = friendlyError(e.message))
            }
        }
    }

    fun signInWithGoogle(credential: AuthCredential, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _uiState.value = AuthUiState(isLoading = true)
            runCatching {
                auth.signInWithCredential(credential).await()
            }.onSuccess {
                _uiState.value = AuthUiState(isSuccess = true)
                onSuccess()
            }.onFailure { e ->
                _uiState.value = AuthUiState(error = friendlyError(e.message))
            }
        }
    }

    fun setError(msg: String) { _uiState.value = AuthUiState(error = msg) }
    fun clearError() { _uiState.value = AuthUiState() }

    private fun friendlyError(msg: String?) = when {
        msg == null -> "Unknown error"
        "password" in msg -> "Invalid password"
        "email" in msg -> "Invalid email"
        "no user" in msg -> "User not found"
        "already in use" in msg -> "Email is already in use"
        "weak" in msg -> "The password is too weak (at least 6 characters)"
        else -> msg
    }
}