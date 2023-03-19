package kz.devs.aiturm.ui.presentation.authentication.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
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

        auth = Firebase.auth

        signIn?.setOnClickListener {
            startActivity(RegistrationActivity.newIntent(this))
        }

        loginButton?.setOnClickListener {
//            startActivity(HomeActivity.newInstance(this))
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
                val regex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$"
                val email: String = emailInputLayout?.editText?.text.toString().trim()
                if (email.isBlank()) {
                    emailInputLayout?.error = getString(R.string.empty_field)
                    loginButton?.isEnabled = false
                } else if (!email.matches(regex.toRegex())) {
                    emailInputLayout?.error = getString(R.string.format_error)
                } else {
                    emailInputLayout?.error = null
                    if (emailInputLayout?.editText?.text.isNullOrBlank() || passwordInputLayout?.editText?.text.isNullOrBlank()) {
                        loginButton?.isEnabled = false
                    } else {
                        loginButton?.isEnabled =
                            emailInputLayout?.error == null && passwordInputLayout?.error == null
                    }
                }
            }
        })
    }

    private fun setupPasswordValidator() {
        passwordInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            passwordInputLayout?.editText as TextInputEditText
        ) {
            override fun validate(editText: TextInputEditText, text: String) {
                val password: String = passwordInputLayout?.editText?.text.toString().trim()
                if (password.isBlank()) {
                    passwordInputLayout?.error = getString(R.string.empty_field)
                    loginButton?.isEnabled = false
                } else {
                    passwordInputLayout?.error = null

                    if (emailInputLayout?.editText?.text.isNullOrBlank() || passwordInputLayout?.editText?.text.isNullOrBlank()) {
                        loginButton?.isEnabled = false
                    } else {
                        loginButton?.isEnabled =
                            emailInputLayout?.error == null && passwordInputLayout?.error == null
                    }
                }
            }
        })
    }

    private fun setupLoginButton() {
        loginButton?.isEnabled = false
    }
}