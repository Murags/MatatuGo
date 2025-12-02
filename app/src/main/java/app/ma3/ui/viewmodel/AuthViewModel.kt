package app.ma3.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.ma3.data.repository.AuthRepository
import app.ma3.data.model.User
import app.ma3.data.preferences.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: AuthRepository = AuthRepository(),
    private val tokenManager: TokenManager? = null
) : ViewModel() {

    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private val _signupState = MutableStateFlow<SignupState>(SignupState.Idle)
    val signupState: StateFlow<SignupState> = _signupState.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    fun login(email: String, password: String) {
        if (email.isBlank() || password.isBlank()) {
            _loginState.value = LoginState.Error("Email and password are required")
            return
        }

        _loginState.value = LoginState.Loading

        viewModelScope.launch {
            authRepository.login(email, password).fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _loginState.value = LoginState.Success(user)
                },
                onFailure = { error ->
                    _loginState.value = LoginState.Error(error.message ?: "Login failed")
                }
            )
        }
    }

    fun signup(name: String, email: String, password: String) {
        if (name.isBlank() || email.isBlank() || password.isBlank()) {
            _signupState.value = SignupState.Error("Name, email and password are required")
            return
        }

        _signupState.value = SignupState.Loading

        viewModelScope.launch {
            authRepository.signup(name, email, password).fold(
                onSuccess = { user ->
                    _currentUser.value = user
                    _signupState.value = SignupState.Success(user)
                },
                onFailure = { error ->
                    _signupState.value = SignupState.Error(error.message ?: "Sign up failed")
                }
            )
        }
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
            _currentUser.value = null
            _loginState.value = LoginState.Idle
        }
    }

    fun resetLoginState() {
        _loginState.value = LoginState.Idle
    }

    fun resetSignupState() {
        _signupState.value = SignupState.Idle
    }

    fun checkAuthState() {
        if (tokenManager?.isLoggedIn() == true) {
            // User is logged in, you could load user data here
            // For now, just set state to logged in
        }
    }
}

sealed class LoginState {
    object Idle : LoginState()
    object Loading : LoginState()
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}

sealed class SignupState {
    object Idle : SignupState()
    object Loading : SignupState()
    data class Success(val user: User) : SignupState()
    data class Error(val message: String) : SignupState()
}
