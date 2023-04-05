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


    //LoginFragment
    private val loginEmailEditText by lazy { MutableLiveData<EmailValidation>() }
    fun getLoginEmailError(): LiveData<EmailValidation> = loginEmailEditText

    private val loginPasswordEditText by lazy { MutableLiveData<PasswordValidator>() }
    fun getLoginPasswordError(): LiveData<PasswordValidator> = loginPasswordEditText

    private val loginButtonAvailability by lazy { MutableLiveData<Boolean>() }
    fun getLoginButtonAvailability(): LiveData<Boolean> = loginButtonAvailability

    private val successfullyLoggedIn by lazy { MutableLiveData<Boolean>() }
    fun getLoginResult(): LiveData<Boolean> = successfullyLoggedIn

    //RegistrationFragment

    private val registerEmailEditText by lazy { MutableLiveData<EmailValidation>() }
    fun getRegistrationEmailError(): LiveData<EmailValidation> = registerEmailEditText

    private val registerPasswordEditText by lazy { MutableLiveData<PasswordValidator>() }
    fun getRegistrationPasswordError(): LiveData<PasswordValidator> = registerPasswordEditText

    private val registerButtonAvailability by lazy { MutableLiveData<Boolean>() }
    fun getRegisterButtonAvailability(): LiveData<Boolean> = registerButtonAvailability

    private val successfullyRegistered by lazy { MutableLiveData<Boolean>() }
    fun getRegistrationResult(): LiveData<Boolean> = successfullyRegistered

    fun onLoginPasswordTextChanged(changedText: String) {
        if (changedText.trim().isBlank()) {
            loginButtonAvailability.value = false
            loginPasswordEditText.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = application.getString(R.string.empty_field)
            )
        } else {
            loginPasswordEditText.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = null
            )
            if (loginEmailEditText.value?.emailText.isNullOrBlank() || loginPasswordEditText.value?.passwordText.isNullOrBlank()) {
                loginButtonAvailability.value = false
            } else {
                loginButtonAvailability.value =
                    loginEmailEditText.value?.errorMessage == null && loginPasswordEditText.value?.errorMessage == null
            }
        }
    }

    fun onLoginEmailTextChanged(changedText: String) {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        if (changedText.isBlank()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    loginButtonAvailability.value = false
                    loginEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.empty_field)
                    )
                }
            }
        } else if (!pattern.matcher(changedText).matches()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    loginButtonAvailability.value = false
                    loginEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.format_error)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Main){
                    loginEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = null
                    )
                    if (loginEmailEditText.value?.emailText.isNullOrBlank() || loginPasswordEditText.value?.passwordText.isNullOrBlank()) {
                        loginButtonAvailability.value = false
                    } else {
                        loginButtonAvailability.value =
                            loginEmailEditText.value?.errorMessage == null && loginPasswordEditText.value?.errorMessage == null
                    }
                }
            }
        }
    }

    fun onSignInButtonClicked(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authRepository.login(email, password).addOnCompleteListener { isCompleted ->
                    successfullyLoggedIn.value = isCompleted.isSuccessful
                }
            }
        }
    }

    //Registration

    fun onRegistrationPasswordTextChanged(changedText: String) {
        val pattern: Pattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}\$")
        if (changedText.trim().isBlank()) {
            registerButtonAvailability.value = false
            registerPasswordEditText.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = application.getString(R.string.empty_field)
            )
        } else if (!pattern.matcher(changedText).matches()) {
            registerButtonAvailability.value = false
            registerPasswordEditText.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = application.getString(R.string.password_regex_is_wrong)
            )
        } else {
            registerPasswordEditText.value = PasswordValidator(
                passwordText = changedText,
                errorMessage = null
            )
            if (registerEmailEditText.value?.emailText.isNullOrBlank() || registerPasswordEditText.value?.passwordText.isNullOrBlank()) {
                registerButtonAvailability.value = false
            } else {
                registerButtonAvailability.value =
                    registerEmailEditText.value?.errorMessage == null && registerPasswordEditText.value?.errorMessage == null
            }
        }
    }


    fun onRegisterEmailTextChanged(changedText: String) {
        val pattern: Pattern = Patterns.EMAIL_ADDRESS
        if (changedText.isBlank()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    registerButtonAvailability.value = false
                    registerEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.empty_field)
                    )
                }
            }
        } else if (!pattern.matcher(changedText).matches()) {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    registerButtonAvailability.value = false
                    registerEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = application.getString(R.string.format_error)
                    )
                }
            }
        } else {
            viewModelScope.launch {
                withContext(Dispatchers.Main) {
                    registerEmailEditText.value = EmailValidation(
                        emailText = changedText,
                        errorMessage = null
                    )
                    if (registerEmailEditText.value?.emailText.isNullOrBlank() || registerPasswordEditText.value?.passwordText.isNullOrBlank()) {
                        registerButtonAvailability.value = false
                    } else {
                        registerButtonAvailability.value =
                            registerEmailEditText.value?.errorMessage == null && registerPasswordEditText.value?.errorMessage == null
                    }
                }
            }
        }
    }


    fun onSignUpClicked(email: String, password: String) {
        viewModelScope.launch {
            withContext(Dispatchers.Main) {
                authRepository.signUp(email, password).addOnCompleteListener { isCompleted ->
                    successfullyRegistered.value = isCompleted.isSuccessful
                }
            }
        }
    }

}