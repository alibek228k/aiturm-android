package kz.devs.aiturm.ui.presentation.authentication.registration.step1

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
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.validation.EditTextValidator

class RegistrationActivity : AppCompatActivity() {

    private var signIn: MaterialTextView? = null
    private var signUpButton: MaterialButton? = null
    private var emailInputLayout: TextInputLayout? = null
    private var passwordInputLayout: TextInputLayout? = null

    private var auth: FirebaseAuth? = null

    companion object {
        fun newIntent(context: Context) = Intent(context, RegistrationActivity::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        signIn = findViewById(R.id.signIn)
        signUpButton = findViewById(R.id.signUpButton)
        emailInputLayout = findViewById(R.id.emailInputLayout)
        passwordInputLayout = findViewById(R.id.passwordInputLayout)

        auth = Firebase.auth

        setupEmailValidator()
        setupPasswordValidator()
        setupSignUpButton()

        signUpButton?.setOnClickListener {
            auth?.let{
                it.createUserWithEmailAndPassword(
                    emailInputLayout?.editText?.text?.toString()!!,
                    passwordInputLayout?.editText?.text?.toString()!!
                ).addOnCompleteListener(this) { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, getString(R.string.successfully_singed_up), Toast.LENGTH_SHORT)
                            .show()
                        onBackPressed()
                    }else {
                        Toast.makeText(this, getString(R.string.failed_to_sing_up), Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            }
        }


        signIn?.setOnClickListener {
            onBackPressed()
        }
    }

    private fun setupEmailValidator(){
        emailInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            emailInputLayout?.editText as TextInputEditText
        ){
            override fun validate(editText: TextInputEditText, text: String) {
                val regex = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
                val email: String = emailInputLayout?.editText?.text.toString().trim()
                if (email.isBlank()) {
                    emailInputLayout?.error = getString(R.string.format_error)
                }else if (email.matches(regex.toRegex())){
                    emailInputLayout?.error = null

                    signUpButton?.isEnabled = emailInputLayout?.error == null && passwordInputLayout?.error == null
                }
            }
        })
    }

    private fun setupPasswordValidator(){
        passwordInputLayout?.editText?.addTextChangedListener(object : EditTextValidator(
            passwordInputLayout?.editText as TextInputEditText
        ){
            override fun validate(editText: TextInputEditText, text: String) {
                val regex = "(?=.*[@#$%^&+=])" +     // at least 1 special character

                        "(?=\\S+$)" +                     // no white spaces

                        ".{4,}$"                              // at least 4 characters
                val password: String = passwordInputLayout?.editText?.text.toString().trim()
                if (password.isBlank()) {
                    passwordInputLayout?.error = getString(R.string.format_error)
                }else if (!password.matches(regex.toRegex())){
                    passwordInputLayout?.error = getString(R.string.format_error)
                } else {
                    passwordInputLayout?.error = null

                    signUpButton?.isEnabled = emailInputLayout?.error == null && passwordInputLayout?.error == null
                }
            }
        })
    }

    private fun setupSignUpButton(){
        signUpButton?.isEnabled = false
    }
}