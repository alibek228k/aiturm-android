package kz.devs.aiturm.ui.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kz.devs.aiturm.R
import kz.devs.aiturm.core.Preferences
import kz.devs.aiturm.ui.presentation.authentication.LoginActivity

class LaunchActivity : AppCompatActivity() {

    private val sharedPreferences = Preferences.getSharedPreferences(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
//        if (sharedPreferences?.getBoolean("isShown", false) == true){
//            startActivity(HomeActivity.newInstance(this))
            super.onCreate(savedInstanceState)
//            finish()
//        }else{
//            startActivity(LoginActivity.newInstance(this))
//        }

        startActivity(LoginActivity.newInstance(this))
    }
}