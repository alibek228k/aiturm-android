package kz.devs.aiturm.ui.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kz.devs.aiturm.R
import kz.devs.aiturm.ui.presentation.authentication.login.LoginActivity

class HomeActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }

    private var signOutButton: MaterialButton? = null

    private var auth: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        signOutButton = findViewById(R.id.signOutButton)

        signOutButton?.setOnClickListener{
            auth.signOut()
            Toast.makeText(this, "Successfully signed out!", Toast.LENGTH_SHORT).show()
            startActivity(LoginActivity.newInstance(this))
        }
    }
}