package kz.devs.aiturm.ui.presentation.authentication.registration.step1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kz.devs.aiturm.R

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

        signUpButton?.setOnClickListener {
//            startActivity(ProfileFillOutActivity.newInstance(this@RegistrationActivity))
            auth?.let{
                it.createUserWithEmailAndPassword(
                    emailInputLayout?.editText?.text?.toString()!!,
                    passwordInputLayout?.editText?.text?.toString()!!
                ).addOnCompleteListener(this) { result ->
                    if (result.isSuccessful) {
                        Toast.makeText(this, "User successfully signed up!", Toast.LENGTH_SHORT)
                            .show()
                        onBackPressed()
                    }else {
                        Toast.makeText(this, "Signed up failed, try again!", Toast.LENGTH_SHORT).show()
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
}