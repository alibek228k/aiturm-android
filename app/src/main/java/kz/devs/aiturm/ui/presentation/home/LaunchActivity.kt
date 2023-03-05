package kz.devs.aiturm.ui.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kz.devs.aiturm.R
import kz.devs.aiturm.core.Preferences
import kz.devs.aiturm.ui.presentation.authentication.login.LoginActivity

class LaunchActivity : AppCompatActivity() {

    private val sharedPreferences = Preferences.getSharedPreferences(this)
    private var auth: FirebaseAuth = Firebase.auth
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
            super.onCreate(savedInstanceState)
        if (auth.currentUser != null){
            startActivity(HomeActivity.newInstance(this))
        }else {
            startActivity(LoginActivity.newInstance(this))
        }
    }
}