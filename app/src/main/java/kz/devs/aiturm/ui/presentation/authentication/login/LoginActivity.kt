package kz.devs.aiturm.ui.presentation.authentication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.presentation.authentication.registration.step1.RegistrationActivity
import kz.devs.aiturm.ui.presentation.home.HomeActivity
import kz.devs.aiturm.ui.validation.EditTextValidator

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private var signIn: MaterialTextView? = null
    private var loginButton: MaterialButton? = null
    private var emailInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null

    private val viewModel: LoginViewModel by viewModels()

    private var auth: FirebaseAuth? = null

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signIn = findViewById(R.id.signUp)
        loginButton = findViewById(R.id.loginButton)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)

        setupEmailValidator()
        setupPasswordValidator()
        setupLoginButton()

        observePasswordErrorMessage()
        observeEmailErrorMessage()
        observeLoginButtonAvailability()

        auth = Firebase.auth

        signIn?.setOnClickListener {
            startActivity(RegistrationActivity.newIntent(this))
        }

        loginButton?.setOnClickListener {
            auth?.let {
                it.signInWithEmailAndPassword(
                    emailInputLayout?.editText?.text?.toString()!!,
                    passwordInputLayout?.editText?.text?.toString()!!
                ).addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "Successfully signed in!", Toast.LENGTH_SHORT).show()
                        startActivity(HomeActivity.newInstance(this))
                    } else {
                        Toast.makeText(this, "Failed to sign in, try again", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }
    }

    private fun setupEmailValidator() {
        emailInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            emailInputLayout?.editText as TextInputEditText
        ) {
            override fun validate(editText: TextInputEditText, text: String) {

            }

            override fun validatedText(text: String) {
                viewModel.onEmailTextChanged(text)
            }
        })
    }

    private fun setupPasswordValidator() {
        passwordInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            passwordInputLayout?.editText as TextInputEditText
        ) {
            override fun validate(editText: TextInputEditText, text: String) {

            }

            override fun validatedText(text: String) {
                viewModel.onPasswordTextChanged(text)
            }
        })
    }

    private fun setupLoginButton() {
        loginButton?.isEnabled = false
    }

    private fun observeEmailErrorMessage(){
        viewModel.getEmailError().observe(this){ validator ->
            emailInputLayout?.error = validator.errorMessage
        }
    }

    private fun observePasswordErrorMessage(){
        viewModel.getPasswordError().observe(this){ validator ->
            passwordInputLayout?.error = validator.errorMessage
        }
    }

    private fun observeLoginButtonAvailability(){
        viewModel.getLoginButtonAvailability().observe(this){
            loginButton?.isEnabled = it
        }
    }
}