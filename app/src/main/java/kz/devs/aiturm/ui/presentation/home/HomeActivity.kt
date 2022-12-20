package kz.devs.aiturm.ui.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.devs.aiturm.R

class HomeActivity : AppCompatActivity() {

    companion object{
        fun newInstance(context: Context): Intent{
            return Intent(context, HomeActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}