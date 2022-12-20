package kz.devs.aiturm.ui.presentation.authentication

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.textview.MaterialTextView
import kz.devs.aiturm.R

class RegistrationActivity : AppCompatActivity() {

    private var signIn: MaterialTextView? = null

    companion object{
        fun newIntent(context: Context) = Intent(context, RegistrationActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        signIn = findViewById(R.id.signIn)

        signIn?.setOnClickListener {
            startActivity(LoginActivity.newInstance(this))
        }
    }
}