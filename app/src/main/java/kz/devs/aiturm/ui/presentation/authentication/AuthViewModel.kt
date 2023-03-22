package kz.devs.aiturm.ui.presentation.authentication

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kz.devs.aiturm.R
import kz.devs.aiturm.domain.repository.AuthRepository
import kz.devs.aiturm.ui.validation.model.EmailValidation
import kz.devs.aiturm.ui.validation.model.PasswordValidator
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val application: Application,
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val emailEditTextValidator by lazy { MutableLiveData<EmailValidation>() }
    fun getEmailError(): LiveData<EmailValidation> = emailEditTextValidator

    private val passwordEditTextValidator by lazy { MutableLiveData<PasswordValidator>() }
    fun getPasswordError(): LiveData<PasswordValidator> = passwordEditTextValidator

    private val loginButtonAvailability by lazy { MutableLiveData<Boolean>() }
    fun getLoginButtonAvailability(): LiveData<Boolean> = loginButtonAvailability

    private val successfullyLoggedIn by lazy { MutableLiveData<Boolean>() }
    fun getLoginResult(): LiveData<Boolean> = successfullyLoggedIn

    fun onPasswordTextChanged(changedText: String) {
        if (changedText.trim().isBlank()) {
            loginButtonAvailability.value = false
            passwordEditTextValidator.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = application.getString(R.string.empty_field)
            )
        } else {
            passwordEditTextValidator.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = null
            )
            if (emailEditTextValidator.value?.emailText.isNullOrBlank() || passwordEditTextValidator.value?.passwordText.isNullOrBlank()) {
                loginButtonAvailability.value = false
            } else {
                loginButtonAvailability.value =
                    emailEditTextValidator.value?.errorMessage == null && passwordEditTextValidator.value?.errorMessage == null
            }
        }
    }

    fun onEmailTextChanged(changedText: String) {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        if (changedText.isBlank()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    loginButtonAvailability.value = false
                    emailEditTextValidator.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.empty_field)
                    )
                }
            }
        } else if (!pattern.matcher(changedText).matches()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    loginButtonAvailability.value = false
                    emailEditTextValidator.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.format_error)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    emailEditTextValidator.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = null
                    )
                    if (emailEditTextValidator.value?.emailText.isNullOrBlank() || passwordEditTextValidator.value?.passwordText.isNullOrBlank()) {
                        loginButtonAvailability.value = false
                    } else {
                        loginButtonAvailability.value =
                            emailEditTextValidator.value?.errorMessage == null && passwordEditTextValidator.value?.errorMessage == null
                    }
                }
            }
        }
    }

    fun onSignInButtonClicked(email: String, password: String){
        viewModelScope.launch {
            withContext(Dispatchers.Main){
                authRepository.login(email, password).addOnCompleteListener { isCompleted ->
                    successfullyLoggedIn.value = isCompleted.isSuccessful
                }
            }
        }
    }

}