package kz.devs.aiturm.ui.presentation.authentication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.presentation.home.HomeActivity

class LoginActivity : AppCompatActivity() {

    private var signIn: MaterialTextView? = null
    private var loginButton: MaterialButton? = null

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signIn = findViewById(R.id.signUp)
        loginButton = findViewById(R.id.loginButton)

        signIn?.setOnClickListener{
            startActivity(RegistrationActivity.newIntent(this))
        }
        loginButton?.setOnClickListener {
            startActivity(HomeActivity.newInstance(this))
        }
    }
}