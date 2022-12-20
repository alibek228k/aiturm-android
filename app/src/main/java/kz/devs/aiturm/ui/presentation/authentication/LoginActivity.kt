package kz.devs.aiturm.ui.presentation.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textview.MaterialTextView
import kz.devs.aiturm.R

class LoginActivity : AppCompatActivity() {

    private var signIn: MaterialTextView? = null

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, LoginActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signIn = findViewById(R.id.signUp)

        signIn?.setOnClickListener{
            startActivity(RegistrationActivity.newIntent(this))
        }
    }
}